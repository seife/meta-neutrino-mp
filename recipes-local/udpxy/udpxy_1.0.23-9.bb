DESCRIPTION = "udpxy is a UDP-to-HTTP multicast traffic relay daemon"
HOMEPAGE = "http://sourceforge.net/projects/udpxy"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${THISDIR}/files/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"

SRC_URI = " \
	http://www.udpxy.com/download/1_23/udpxy.${PV}-prod.tar.gz \
	file://GPL-3.0 \
	file://udpxy.init \
	file://udpxy.default \
"

# S = "${WORKDIR}/udpxy-1.0.23-9"
inherit autotools update-rc.d

INITSCRIPT_NAME = "udpxy"
INITSCRIPT_PARAMS = "defaults"

do_compile () {
	oe_runmake rdebug
}

do_install(){
	install -d ${D}/${bindir}
	install -m 755 -D ${WORKDIR}/${PN}.init ${D}${sysconfdir}/init.d/${PN}
	install -m 644 -D ${WORKDIR}/${PN}.default ${D}${sysconfdir}/default/${PN}
	install -m 755 ${S}/udpxy ${D}/${bindir}/
	install -m 755 ${S}/udpxrec ${D}/${bindir}/
}

SRC_URI[md5sum] = "0c953f7dd80329c1a062997afb9b6744"
SRC_URI[sha256sum] = "6ce33b1d14a1aeab4bd2566aca112e41943df4d002a7678d9a715108e6b714bd"
