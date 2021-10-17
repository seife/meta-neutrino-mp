DESCRIPTION = "Mediathek Lua plugin"
AUTHOR = "Michael Liebmann"
LICENSE = "GPL-2.0+"

PACKAGE_ARCH = "all"

LIC_FILES_CHKSUM = " \
	file://COPYING;md5=751419260aa954499f7abaabaa882bbe \
"

SRCREV = "${AUTOREV}"
P_V = "0.3~beta-2"
PV = "${P_V}+git${SRCPV}"
PR = "r0"

SOURCE_GITHUB = "git://github.com/neutrino-mediathek/mediathek.git;branch=master;protocol=https"

SRC_URI = " \
	${SOURCE_GITHUB} \
"

RDEPENDS_${PN} = "lua-json"
RREPLACES_${PN} = "coolithek"

S = "${WORKDIR}/git"

do_configure () {
	# fail early, if the code version has changed against our package version
	eval $(lua -e 'configfile = {}; function configfile.new() end; pluginTmpPath="";pluginScriptPath=""; M={GetRevision=function() end}; l={key={}};H={fileExist=function() end};dofile("plugins/neutrino-mediathek/variables.lua");initVars();print("PLUGINVERSION=\""..pluginVersion.."\"")')
	#eval `awk '/pluginVersion[[:space:]]*=/{print "PLUGINVERSION="$3}' plugins/neutrino-mediathek/variables.lua`
	PLUGINVERSION="${PLUGINVERSION// /}"
	PACKAGEVERSION=${P_V}
	PACKAGEVERSION=${PACKAGEVERSION//[~-]/}
	echo "pluginversion='$PLUGINVERSION' packageversion='${PACKAGEVERSION}'"
	test x$PLUGINVERSION = x${PACKAGEVERSION}
}

do_install () {
	install -d ${D}/${libdir}/tuxbox/plugins
	install -d ${D}/${datadir}/lua/5.2
	cp -r --preserve=timestamps ${S}/plugins/*  ${D}/${libdir}/tuxbox/plugins
	cp -r --preserve=timestamps ${S}/share/lua  ${D}/${datadir}/
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
		pic2m2v ${libdir}/tuxbox/plugins/neutrino-mediathek/background.jpg
	fi
}

