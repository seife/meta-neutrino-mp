#!/bin/bash
#
# helper script to set up everything to build
# a neutrino image with yocto/OE/meta-neutrino-mp
#
# (C) 2015 Stefan Seyfried
# License: WTFPLv2

usage() {
	echo "usage: `basename $0` <machine>"
	echo "supported machines are:"
	echo " * \"tripledragon\" -- the TripleDragon Armas DBS3000 set-top-box"
	echo " * \"coolstream\" -- Coolstream HD1 set-top-box (so-called \"Nevis\" platform"
	echo " * \"raspberrypi\" -- the RaspberryPi computer"
	echo " * \"spark\" -- Fulan SPARK (STi7111 and STi7162 based) set-top-boxes"
	echo
	exit 1
}

if [ -z "$1" ]; then
	usage
fi
case "$1" in
	tripledragon)	MACH=tripledragon;	APPEND=td;	YOCTO_BRANCH=daisy	;;
	coolstream)	MACH=coolstream;	APPEND=hd1				;;
	raspberrypi)	MACH=raspberrypi;	APPEND=rpi				;;
	spark)		MACH=spark;		APPEND=stl;	META_BSP=meta-stlinux	;;
	*)		usage	;;
esac

BUILD=build-$APPEND
DEST=yocto-poky-$APPEND
YOCTO_BRANCH=${YOCTO_BRANCH:-dizzy}
META_BSP=${META_BSP:-meta-$MACH}

echo "preparing checkout for the following repos:"
echo " * yocto-poky, branch $YOCTO_BRANCH"
echo " * $META_BSP"
echo " * meta-neutrino-mp"
echo "and preparing build for platform $MACH"
echo
read -p "continue? (press ctrl-c to abort) "

set -e
git clone http://git.yoctoproject.org/git/poky $DEST
cd $DEST
git checkout -b $YOCTO_BRANCH origin/$YOCTO_BRANCH
git clone https://github.com/seife/${META_BSP}.git
git clone https://github.com/seife/meta-neutrino-mp.git
case $PATH in
	*:.:*) export PATH=${PATH/:.:/:} ;;
esac
. ./oe-init-build-env $BUILD

cat << EOF >> conf/local.conf
###
MACHINE = "$MACH"
PACKAGE_CLASSES = "package_ipk"
EXTRA_IMAGE_FEATURES += "package-management"
PRSERV_HOST = "localhost:0"

## tune this if you have a common download dir:
#DL_DIR ?= "/local/seife/src/Archive/yocto"
#SOURCE_MIRROR_URL ?= "file:///local/seife/src/Archive"
#INHERIT += "own-mirrors"

## if you want to use ccache...
#INHERIT += "ccache"

## save some space by cleaning up after every build
#INHERIT += "rm_work"

EOF

sed -i -e "/meta-yocto-bsp/{p;s/meta-yocto-bsp/${META_BSP}/p;s/${META_BSP}/meta-neutrino-mp/}" \
	conf/bblayers.conf

set +e
echo
echo
echo "Setup complete. Now change into the '$DEST' directory,"
echo "run '. oe-init-build-env $BUILD' and then 'bitbake neutrino-image'"
echo
echo "Have a lot of fun..."
echo
