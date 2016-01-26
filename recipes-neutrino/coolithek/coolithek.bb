DESCRIPTION = "Mediathek Lua plugin"
AUTHOR = "Michael Liebmann"
LICENSE = "GPL-2.0+"

PACKAGE_ARCH = "all"

LIC_FILES_CHKSUM = " \
	file://coolithek/COPYING;md5=751419260aa954499f7abaabaa882bbe \
"

SRCREV = "${AUTOREV}"
P_V = "0.2beta-10"
PV = "${P_V}+git${SRCPV}"
PR = "r0"

SRC_URI = " \
	git://git.slknet.de/git/mediathek-luaV2.git;branch=master \
"

RDEPENDS_${PN} = "lua-json"

S = "${WORKDIR}/git"

do_configure () {
	# fail early, if the code version has changed against our package version
	eval `awk '/pluginVersion[[:space:]]*=/{print "PLUGINVERSION="$3}' coolithek/variables.lua`
	echo "pluginversion='$PLUGINVERSION' packageversion='${P_V}'"
	test x$PLUGINVERSION = x${P_V}
}

do_install () {
	install -d ${D}/${libdir}/tuxbox/plugins
	install -d ${D}/${datadir}/lua/5.2
	cp -r --preserve=timestamps ${S}/coolithek*  ${D}/${libdir}/tuxbox/plugins
	cp -r --preserve=timestamps ${S}/share/lua   ${D}/${datadir}/
}

FILES_${PN} = " \
	${libdir}/tuxbox/plugins \
	${datadir}/lua/5.2 \
"

# no need for -dev and -dbg..
PACKAGES = "${PN}"

pkg_postinst_${PN} () {
	# pic2m2v is only available on platforms that use "real" libstb-hal
	if which pic2m2v >/dev/null 2>&1; then
		pic2m2v ${libdir}/tuxbox/plugins/coolithek/background.jpg
	fi
}

