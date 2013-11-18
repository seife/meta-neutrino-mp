DESCRIPTION = "luaposix is a POSIX binding, including curses, for Lua 5.1 and 5.2."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=0de24acdcec9cb21ff095356966cbef2"
HOMEPAGE = "https://github.com/luaposix/luaposix"

DEPENDS += "lua5.2-native lua5.2 ncurses"
PR = "r0.1"
SRC_URI = "https://github.com/luaposix/luaposix/archive/v5.1.28.tar.gz \
           file://fix_crosscompile.patch \
           "
S = "${WORKDIR}/luaposix-${PV}"

inherit autotools pkgconfig 

TARGET_CC_ARCH += " -fPIC ${LDFLAGS}"
EXTRA_OEMAKE = "'CC=${CC} -fPIC' 'MYCFLAGS=${CFLAGS} -DLUA_USE_LINUX -fPIC' MYLDFLAGS='${LDFLAGS}'"
EXTRA_OECONF += "--datadir=${datadir}/lua/5.2 --libdir=${libdir}/lua/5.2"

do_configure_prepend() {
	./bootstrap
}


BBCLASSEXTEND = "native"

FILES_${PN}-dbg += "${libdir}/lua/5.2/.debug"
FILES_${PN}-dev += "${libdir}/lua/5.2/*.la"
FILES_${PN} += "${libdir}/lua/5.2/*.so"
FILES_${PN} += "${datadir}/lua/5.2/*.lua"

SRC_URI[md5sum] = "0ea20ea21bd9f92b79041beb6d41393e"
SRC_URI[sha256sum] = "52e55d6d59007b70a05c7ab98a7d403e897e9601a72eb2fa1f1a6f95ece6086d"
