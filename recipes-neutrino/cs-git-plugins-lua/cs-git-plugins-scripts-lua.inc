HOMEPAGE = "http://git.coolstreamtech.de/"
DEPENDS = "lua5.2"
RDEPENDS_${PN} = "lua-json luaposix"

SRCREV = "${AUTOREV}"
PV = "0.0+git${SRCPV}"

# all plain LUA, so no -dev and -dbg needed
PACKAGES = "${PN}"
inherit allarch

SRC_URI = " \
	git://coolstreamtech.de/cst-public-plugins-scripts-lua.git \
"

S = "${WORKDIR}/git/plugins"

FILES_${PN} = " \
	${libdir}/tuxbox/plugins \
"
