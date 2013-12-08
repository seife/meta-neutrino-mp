#!/bin/sh
#
test x"$1" != x"start" && exit 0
# for some reason, udev devices that are already plugged at boot
# are not handled by udevadm trigger... at least on old kernels.
# just do them manually
for i in /sys/block/sd*/uevent /sys/block/sd*/sd*/uevent; do
	test -e $i || continue
	echo "add" > $i
done
