#!/bin/sh
#
# /media should be mounted as tmpfs, so that the
# stuff udev puts there is cleaned up at reboot

test -e /media/.notmpfs && exit 0
test x"$1" != x"start" && exit 0
# already mounted?
grep "^tmpfs.*/media$" /proc/mounts && exit 0

mount -t tmpfs tmpfs /media -osize=512k
