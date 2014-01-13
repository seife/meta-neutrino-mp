DESCRIPTION = "xupnpd - eXtensible UPnP agent"
HOMEPAGE = "http://xupnpd.org"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS += "lua5.2"
SRCREV = "392"
SRC_URI = "\
	svn://tsdemuxer.googlecode.com/svn/trunk;protocol=http;module=xupnpd \
	file://xupnpd.init \
	file://xupnpd.lua \
	file://xupnpd_coolstream.lua \
"

PV = "0.0+svn${SRCREV}"
PR = "r2"
S = "${WORKDIR}/xupnpd/src"

inherit base update-rc.d

INITSCRIPT_NAME = "xupnpd"
INITSCRIPT_PARAMS = "defaults"

# this is very ugly, but the xupnpd makefile is utter crap :-(
SRC = "main.cpp soap.cpp mem.cpp mcast.cpp luaxlib.cpp luaxcore.cpp luajson.cpp luajson_parser.cpp"

do_compile () {
	${CC} -O2 -c -o md5.o md5c.c
	${CC} ${CFLAGS} ${LDFLAGS} -DWITH_URANDOM -o xupnpd ${SRC} md5.o -llua -lm -ldl -lstdc++
}


do_install () {
	install -d -m 0644 ${D}/usr/share/xupnpd/config ${D}/usr/share/xupnpd/playlists
	install -D -m 0755 ${WORKDIR}/xupnpd.init ${D}${sysconfdir}/init.d/xupnpd
	install -D -m 0755 ${S}/xupnpd ${D}${bindir}/xupnpd
	cp -r ${S}/plugins	${D}/usr/share/xupnpd/
	cp ${WORKDIR}/xupnpd_coolstream.lua ${D}/usr/share/xupnpd/plugins/
	cp -r ${S}/profiles	${D}/usr/share/xupnpd/
	cp -r ${S}/ui		${D}/usr/share/xupnpd/
	cp -r ${S}/www		${D}/usr/share/xupnpd/
	cp ${S}/*.lua		${D}/usr/share/xupnpd/
	cp ${WORKDIR}/xupnpd.lua ${D}/usr/share/xupnpd/
}
