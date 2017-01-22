FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# PRINC is now deprecated...
#PRINC := "${@int(PRINC) + 2}"

PR .= ".2"

SRC_URI += " \
	file://neutrino-busybox.cfg \
	file://telnetd.busybox \
	file://hostname.script \
"

PACKAGES_prepend += "${PN}-inetd ${PN}-telnetd "
FILES_${PN}-inetd = " \
	/etc/init.d/inetd.busybox \
	/etc/inetd.conf \
"
FILES_${PN}-telnetd = " \
	/etc/init.d/telnetd.busybox \
"

RRECOMMENDS_${PN} += "${PN}-inetd ${PN}-telnetd"

INITSCRIPT_PACKAGES += "${PN}-inetd ${PN}-telnetd"

INITSCRIPT_NAME_${PN}-inetd = "inetd.busybox"
INITSCRIPT_NAME_${PN}-telnetd = "telnetd.busybox"
INITSCRIPT_PARAMS_${PN}-inetd = "defaults"
INITSCRIPT_PARAMS_${PN}-telnetd = "defaults"
##
## avoid update-alternatives hell for syslog -- it just does not work.
INITSCRIPT_NAME_${PN}-syslog = "syslog.busybox"
ALTERNATIVE_busybox-syslog_remove = "syslog-init"
# just make sure the other provider is not installed
RCONFLICTS_${PN}-syslog = "sysklogd"

do_install_append() {
	if grep "CONFIG_TELNETD=y" ${B}/.config; then
		install -m 0755 ${WORKDIR}/telnetd.busybox ${D}${sysconfdir}/init.d/telnetd.${BPN}
	fi
	if grep "CONFIG_UDHCPC=y" ${B}/.config; then
		# the directory was created already before in do_install()
		install -m 0755 ${WORKDIR}/hostname.script ${D}${sysconfdir}/udhcpc.d/51hostname
	fi
}

pkg_prerm_${PN}-telnetd () {
#!/bin/sh
# do not stop telnetd on update, or uninstall is impossible
# while being logged in via telnet
exit 0
}

pkg_preinst_${PN}-telnetd() {
#!/bin/sh
if test "x$D" != "x"; then
	OPT="-r $D"
fi
if type update-rc.d >/dev/null 2>/dev/null; then
	update-rc.d -f $OPT telnetd.busybox remove
fi
exit
}

pkg_postinst_${PN}-telnetd() {
#!/bin/sh
if test "x$D" != "x"; then
	OPT="-r $D"
fi
if type update-rc.d >/dev/null 2>/dev/null; then
	update-rc.d $OPT telnetd.busybox defaults
	# "restart" as done by "update-rc.d -s" is deadly for existing connections
	test "x$D" = "x" && /etc/init.d/telnetd.busybox start
fi
exit
}

pkg_prerm_${PN}-syslog() {
	### hack
	# remove syslog
	if test "x$D" = "x"; then
		if test "$1" = "upgrade" -o "$1" = "remove"; then
			/etc/init.d/syslog.busybox stop
		fi
	fi
	update-alternatives --remove  syslog-conf /etc/syslog.conf.busybox
	update-alternatives --remove  syslog-startup-conf /etc/syslog-startup.conf.busybox
	exit 0
	# exit here, because OE recipe puts another "/etc/init.d/syslog stop" here
	# which often fails or at least makes the above logic completely useless
	####
}

pkg_prerm_${PN}_append() {
	# update-alternatives has !/bin/sh hardcoded, which will get killed during prerm
	UA=`type -p update-alternatives`
	if [ -n "$UA" ]; then
		sed -n '2,$p' $UA > $tmpdir/update-alternatives
		chmod 755 $tmpdir/update-alternatives
	fi
}
