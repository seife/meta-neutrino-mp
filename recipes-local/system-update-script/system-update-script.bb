SUMMARY = "System update script"
DESCRIPTION = "This package provides a system update script which works around opkg bugs"
SECTION = "base"
LICENSE = "WTFPLv2"
LIC_FILES_CHKSUM = "file://copying.wtfpl;md5=389a9e29629d1f05e115f8f05c283df5"

PV = "0.42.2"

inherit allarch

SRC_URI = " \
	file://system-update-opkg.sh \
	file://copying.wtfpl \
"
S = "${WORKDIR}"

do_install () {
	install -d ${D}${bindir}/
	install -m 0755 ${WORKDIR}/system-update-opkg.sh ${D}${bindir}/
	ln -s system-update-opkg.sh ${D}${bindir}/system-update
}
