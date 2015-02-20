DESCRIPTION = "xupnpd Lua plugins"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = " \
	file://../plugins/ard_mediathek/ard_mediathek.lua;beginline=1;endline=20;md5=cc9f2ac0e48626fcc38baccfc9344558 \
"

include cs-git-plugins-scripts-lua.inc

PR = "r0"

RDEPENDS_${PN} = "xupnpd curl"

SRC_URI += " \
	file://0001-xupnpd_youtube_fix.patch;striplevel=2 \
"

S = "${WORKDIR}/git/xupnpd"

do_install () {
	install -d ${D}/usr/share/xupnpd/plugins
	install -m 644 ${S}/xupnpd_18plus.lua ${D}/usr/share/xupnpd/plugins/
	install -m 644 ${S}/xupnpd_cczwei.lua ${D}/usr/share/xupnpd/plugins/
	install -m 644 ${S}/xupnpd_coolstream.lua ${D}/usr/share/xupnpd/plugins/
	# youtube2 to not conflict with curretn xupnpd package -- need fixing!
	install -m 644 ${S}/xupnpd_youtube.lua ${D}/usr/share/xupnpd/plugins/xupnpd_youtube2.lua
}

FILES_${PN} = "\
	/usr/share/xupnpd/plugins \
"
