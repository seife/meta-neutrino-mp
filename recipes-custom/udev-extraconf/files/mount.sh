#!/bin/sh
#
# Called from udev
#
# Attempt to mount any added block devices and umount any removed devices
#
# adapted from yocto meta/recipes-core/udev/udev-extraconf/mount.sh
# to also create symlinks with persistent names by Stefan Seyfried

MOUNT="/bin/mount"
PMOUNT="/usr/bin/pmount"
UMOUNT="/bin/umount"
for line in `grep -v ^# /etc/udev/mount.blacklist`
do
	if [ ` expr match "$DEVNAME" "$line" ` -gt 0 ];
	then
		logger "udev/mount.sh" "[$DEVNAME] is blacklisted, ignoring"
		exit 0
	fi
done

automount() {
	name="`basename "$DEVNAME"`"

	! test -d "/media/$name" && mkdir -p "/media/$name"
	# Silent util-linux's version of mounting auto
	if [ "x`readlink $MOUNT`" = "x/bin/mount.util-linux" ] ;
	then
		MOUNT="$MOUNT -o silent"
	fi

	# If filesystem type is vfat, change the ownership group to 'disk', and
	# grant it with  w/r/x permissions.
	case $ID_FS_TYPE in
	vfat|fat)
		MOUNT="$MOUNT -o umask=007,gid=`awk -F':' '/^disk/{print $3}' /etc/group`"
		;;
	# TODO
	*)
		;;
	esac

	if ! $MOUNT -t auto $DEVNAME "/media/$name"
	then
		#logger "mount.sh/automount" "$MOUNT -t auto $DEVNAME \"/media/$name\" failed!"
		rm_dir "/media/$name"
	else
		logger "mount.sh/automount" "Auto-mount of [/media/$name] successful"
		touch "/tmp/.automount-$name"
		for i in $DEVLINKS; do
			LINK=${i#/dev/disk/}
			DIR=${LINK%/*}
			mkdir /media/$DIR
			rm -f "/media/$LINK"
			ln -s ../$name "/media/$LINK"
		done
	fi
}

rm_dir() {
	rmdir "$1" || logger "mount.sh/automount" "Cannot remove directory [$1]. Not empty?"
	L_TARGET=${1#/media/}
	# now remove the links
	cd /media
	for i in $(find * -maxdepth 1 -type l); do
		[ x`readlink $i` = x../$L_TARGET ] && rm $i
		rmdir ${i%/*}
	done
}

# No ID_FS_TYPE for cdrom device, yet it should be mounted
name="`basename "$DEVNAME"`"
[ -e /sys/block/$name/device/media ] && media_type=`cat /sys/block/$name/device/media`

if [ "$ACTION" = "add" ] && [ -n "$DEVNAME" ] && [ -n "$ID_FS_TYPE" -o "$media_type" = "cdrom" ]; then
	if [ -x "$PMOUNT" ]; then
		$PMOUNT $DEVNAME 2> /dev/null
	elif [ -x $MOUNT ]; then
		$MOUNT $DEVNAME 2> /dev/null
	fi

	# If the device isn't mounted at this point, it isn't
	# configured in fstab (note the root filesystem can show up as
	# /dev/root in /proc/mounts, so check the device number too)
	if expr $MAJOR "*" 256 + $MINOR != `stat -c %d /`; then
		grep -q "^$DEVNAME " /proc/mounts || automount
	fi
	if [ -x /usr/bin/mdev_helper ]; then
		/usr/bin/mdev_helper
	fi
fi



if [ "$ACTION" = "remove" ] && [ -x "$UMOUNT" ] && [ -n "$DEVNAME" ]; then
	for mnt in `grep "$DEVNAME" /proc/mounts | cut -f 2 -d " " `
	do
		$UMOUNT $mnt
	done

	if [ -x /usr/bin/mdev_helper ]; then
		/usr/bin/mdev_helper
	fi

	# Remove empty directories from auto-mounter
	name="`basename "$DEVNAME"`"
	test -e "/tmp/.automount-$name" && rm_dir "/media/$name"
fi
