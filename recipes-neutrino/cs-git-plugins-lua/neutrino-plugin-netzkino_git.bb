DESCRIPTION = "Netzkino Lua plugin"
LICENSE = "MIT"
LIC_FILES_CHKSUM = " \
	file://netzkino/netzkino.lua;beginline=1;endline=24;md5=4fb5aac99d408727fd0f5c63be64fca5 \
"

include cs-git-plugins-scripts-lua.inc

PR = "r0"

do_install () {
	install -d ${D}/${libdir}/tuxbox/plugins
	install -m 755 ${S}/netzkino/netzkino.* ${D}/${libdir}/tuxbox/plugins
}
