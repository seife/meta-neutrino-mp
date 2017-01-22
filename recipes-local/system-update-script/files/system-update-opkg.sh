#!/bin/sh
#
# This program is free software. It comes without any warranty, to
# the extent permitted by applicable law. You can redistribute it
# and/or modify it under the terms of the Do What The Fuck You Want
# To Public License, Version 2, as published by Sam Hocevar. See
# http://www.wtfpl.net/ for more details.
#
# this script works around opkg bugs / weirdness:
# * opkg self-update seems to fail randomly
# * opkg updating busybox, running prerm which will remove wget
# * missing wget makes all other updates fail
# * then busybox postinst is run, recreating wget
#

# first, update package database
opkg update
# update all opkg packages, multiple tries
if opkg list-upgradable | grep "^opkg "; then
	for PKG in opkg-arch-config update-alternatives-opkg opkg; do
		SUCCESS=false
		for i in 1 2 3; do
			if opkg install --force-reinstall $PKG; then
				SUCCESS=true
				break
			fi
		echo "================================================"
		echo "retrying installation of $PKG... $i"
		echo "================================================"
		done
		if ! $SUCCESS; then
			echo "================================"
			echo "$PKG failed $i times"
			echo "================================"
			exit 1
		fi
	done
	opkg configure
fi
# now update busybox, or all others will fail
if opkg list-upgradable | grep "^busybox "; then
	opkg install busybox
	if [ $? != 0 ]; then
		echo "================================================"
		echo "busybox failed"
		echo "================================================"
		exit 2
	fi
fi
# sysvinit also leads to strange results => put it first
if opkg list-upgradable | grep "^sysvinit "; then
	opkg install sysvinit
fi
#
# now the standard upgrade...
opkg upgrade
