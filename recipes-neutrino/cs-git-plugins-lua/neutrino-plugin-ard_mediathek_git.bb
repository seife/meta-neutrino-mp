DESCRIPTION = "ARD Mediathek Lua plugin"
LICENSE = "GPL-2.0+"
LIC_FILES_CHKSUM = " \
	file://ard_mediathek/ard_mediathek.lua;beginline=1;endline=20;md5=cc9f2ac0e48626fcc38baccfc9344558 \
"

include cs-git-plugins-scripts-lua.inc

PR = "r0"

do_install () {
	install -d ${D}/${libdir}/tuxbox/plugins
	install -m 755 ${S}/ard_mediathek/ard_mediathek.lua ${D}/${libdir}/tuxbox/plugins
	install -m 644 ${S}/ard_mediathek/ard_mediathek.jpg ${D}/${libdir}/tuxbox/plugins
	install -m 644 ${S}/ard_mediathek/ard_mediathek.cfg ${D}/${libdir}/tuxbox/plugins
}
