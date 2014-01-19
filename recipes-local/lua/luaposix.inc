DESCRIPTION = "luaposix is a POSIX binding, including curses, for Lua 5.1 and 5.2."
LICENSE = "MIT"
HOMEPAGE = "https://github.com/luaposix/luaposix"

DEPENDS += "lua5.2-native lua5.2 ncurses"

SRC_URI = "https://github.com/luaposix/luaposix/archive/release-v${PV}.tar.gz"
S = "${WORKDIR}/luaposix-release-v${PV}"

inherit autotools pkgconfig

TARGET_CC_ARCH += " -fPIC ${LDFLAGS}"
EXTRA_OEMAKE = "'CC=${CC} -fPIC' 'MYCFLAGS=${CFLAGS} -DLUA_USE_LINUX -fPIC' MYLDFLAGS='${LDFLAGS}'"
EXTRA_OECONF += "--datadir=${datadir}/lua/5.2 --libdir=${libdir}/lua/5.2"

BBCLASSEXTEND = "native"

FILES_${PN}-dbg += "${libdir}/lua/5.2/.debug"
FILES_${PN}-dev += "${libdir}/lua/5.2/*.la"
FILES_${PN} += "${libdir}/lua/5.2/*.so"
FILES_${PN} += "${datadir}/lua/5.2/*.lua"
