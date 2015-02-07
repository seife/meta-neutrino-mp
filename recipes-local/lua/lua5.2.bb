DESCRIPTION = "Lua is a powerful light-weight programming language designed \
for extending applications."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://doc/readme.html;beginline=365;endline=399;md5=ad3069a42853ac3efd7d379d87f6088b"
HOMEPAGE = "http://www.lua.org/"

PV = "5.2.3"

# readline pulls in a heap of runtime dependencies...
DEPENDS += "readline"
PR = "r0"
SRC_URI = "http://www.lua.org/ftp/lua-${PV}.tar.gz \
           file://lua5.2.pc \
          "
S = "${WORKDIR}/lua-${PV}"

inherit pkgconfig binconfig

TARGET_CC_ARCH += " -fPIC ${LDFLAGS}"
EXTRA_OEMAKE = "'CC=${CC} -fPIC' 'MYCFLAGS=${CFLAGS} -DLUA_USE_LINUX -fPIC' MYLDFLAGS='${LDFLAGS}'"

do_configure_prepend() {
	sed -i -e s:/usr/local:${prefix}:g src/luaconf.h
}

do_compile () {
	oe_runmake linux
}

do_install () {
	oe_runmake \
		'INSTALL_TOP=${D}${prefix}' \
		'INSTALL_BIN=${D}${bindir}' \
		'INSTALL_INC=${D}${includedir}/' \
		'INSTALL_MAN=${D}${mandir}/man1' \
		'INSTALL_SHARE=${D}${datadir}/lua' \
		install
	install -d ${D}${libdir}/pkgconfig
	sed "s/^Version:.*/Version: ${PV}/" ${WORKDIR}/lua5.2.pc >  ${D}${libdir}/pkgconfig/lua5.2.pc
}
BBCLASSEXTEND = "native"

FILES_${PN} += "${libdir}/lua"
FILES_${PN} += "${datadir}/lua"

RDEPENDS_${PN}-dev_append = " ${PN}-staticdev"

SRC_URI[md5sum] = "dc7f94ec6ff15c985d2d6ad0f1b35654"
SRC_URI[sha256sum] = "13c2fb97961381f7d06d5b5cea55b743c163800896fd5c5e2356201d3619002d"
