FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
	file://neutrino-busybox.cfg \
	file://telnetd.busybox \
"

PACKAGES_prepend += "${PN}-inetd ${PN}-telnetd"
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

do_install_append() {
	if grep "CONFIG_TELNETD=y" ${B}/.config; then
		install -m 0755 ${WORKDIR}/telnetd.busybox ${D}${sysconfdir}/init.d/telnetd.${BPN}
	fi
}
