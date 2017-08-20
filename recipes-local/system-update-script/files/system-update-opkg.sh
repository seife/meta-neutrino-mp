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
# use --volatile-cache if not configured differently via $OPKG env variable
# this helps avoiding filling up /var/cache on space-limited boxes
if opkg --volatile-cache print-architecture 2>&1 > /dev/null | grep -q "Duplicate boolean option volatile_cache"; then
	: ${OPKG:=opkg}	# already set in config file...
else
	: ${OPKG:=opkg --volatile-cache}
fi

# first, update package database
$OPKG update
# now try to self-update
if $OPKG list-upgradable | grep "^system-update-script "; then
	if $OPKG install system-update-script; then
		# call the symlinked name, so that this script can be renamed later
		exec system-update
	fi
fi
# update all opkg packages, multiple tries
if $OPKG list-upgradable | grep "^opkg "; then
	for PKG in opkg-arch-config update-alternatives-opkg opkg; do
		SUCCESS=false
		for i in 1 2 3; do
			if $OPKG install --force-reinstall $PKG; then
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
	$OPKG configure
fi
# now update busybox, or all others will fail
# sysvinit also leads to strange results => handle outside "opkg upgrade"
for PKG in busybox sysvinit; do
	if $OPKG list-upgradable | grep "^$PKG "; then
		$OPKG install $PKG
		if [ $? != 0 ]; then
			echo "================================================"
			echo "$PKG failed"
			echo "================================================"
			exit 2
		fi
	fi
done
##
## magic string to reset the error flag in the opkg updater gui
echo "system-update: err_reset"
echo "Running upgrade now..."
# now the standard upgrade...
$OPKG upgrade
