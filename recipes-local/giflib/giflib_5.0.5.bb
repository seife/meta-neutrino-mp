DESCRIPTION = "shared library for GIF images"
SECTION = "libs"
LICENSE = "MIT"
PR = "r3"

LIC_FILES_CHKSUM = "file://COPYING;md5=ae11c61b04b2917be39b11f78d71519a"

SRC_URI = "${SOURCEFORGE_MIRROR}/giflib/giflib-${PV}.tar.bz2"
SRC_URI[md5sum] = "c3262ba0a3dad31ba876fb5ba1d71a02"
SRC_URI[sha256sum] = "606d8a366b1c625ab60d62faeca807a799a2b9e88cbdf2a02bfcdf4429bf8609"

inherit autotools lib_package

PACKAGES =+ "${PN}-utils"

FILES_${PN}-utils = "${bindir}/*"
