DESCRIPTION = "Minisatip is a multi-threaded satip version 1.2 server"
LICENSE = "GPL-2.0+"
LIC_FILES_CHKSUM = " \
	file://minisatip.c;beginline=4;endline=7;md5=7a0d0f37f38de4d6e61a03d518a9e16d \
"
HOMEPAGE = "https://toda.ro/forum/"
DEPENDS = "libdvbcsa"

SRC_URI = " \
	git://github.com/catalinii/minisatip.git;protocol=http \
	file://minisatip-strange-drivers.diff \
	file://minisatip.init \
"

SRCREV = "${AUTOREV}"
UPSTREAMVERSION = "0.5.10"
PV = "${UPSTREAMVERSION}+git${SRCPV}"

S = "${WORKDIR}/git"

## EXTRA_OEMAKE_append = " DVBCSA=yes DVBCA=no "
LDFLAGS_append = " -lpthread -lrt -ldvbcsa "
CFLAGS_append = " -DDISABLE_DVBCA "

INITSCRIPT_NAME = "minisatip"

do_install () {
	install -d -m 0755 ${D}/${bindir}
	install -d -m 0755 ${D}/${datadir}/${PN}
	install -d -m 0755 ${D}/etc/init.d
	install -m 0755 minisatip ${D}/${bindir}/
	install -m 0755 ${WORKDIR}/minisatip.init ${D}/etc/init.d/minisatip
	cp -r --preserve=timestamps html ${D}/${datadir}/${PN}
}
