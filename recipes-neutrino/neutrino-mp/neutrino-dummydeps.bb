DESCRIPTION = "dummy dependency package for setting up the SDK."
SUMMARY = "Package that has all the dependencies of neutrino-mp / libstb-hal to properly set up the SDK."
SECTION = "libs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING.GPL;md5=751419260aa954499f7abaabaa882bbe \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/neutrino-mp:"

# even if I'd like it to be otherwise, right now neutrino is machine specific, not CPU specific
PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS += " \
	curl \
	libid3tag \
	libmad \
	freetype \
	giflib \
	libpng \
	jpeg \
	libdvbsi++ \
	ffmpeg \
	flac \
	tremor \
	openthreads \
	lua5.2 \
	luaposix \
	libsigc++-2.0 \
"

RDEPENDS_${PN} += " \
	curl \
	libid3tag \
	libmad \
	freetype \
	giflib \
	libpng \
	jpeg \
	libdvbsi++ \
	ffmpeg \
	flac \
	tremor \
	openthreads \
	lua5.2 \
	luaposix \
	libsigc++-2.0 \
"

DEPENDS_append_coolstream = "\
	virtual/stb-hal-libs \
"

DEPENDS_append_raspberrypi = "\
	virtual/egl \
"

RDEPENDS_${PN}_append_coolstream = "\
	virtual/stb-hal-libs \
"


RDEPENDS_${PN} += " \
	tzdata \
	luaposix \
"

# maybe this should rather be in the image dependencies? it is not needed for building...
DEPENDS_append_tripledragon = " \
	td-dvb-wrapper \
"
RDEPENDS_${PN}_append_tripledragon += "kernel-module-td-dvb-frontend"

##### from libstb-hal:
RDEPENDS_${PN}_append_spark += " tdt-driver libass"
### does not work, not RPROVIDED
###RDEPENDS_${PN}_append_raspberrypi += " virtual/egl"
RDEPENDS_${PN}_append_tripledragon += " directfb triple-sdk"

RDEPENDS_${PN} += "ffmpeg"
RDEPENDS_${PN}-dev_append_spark = " tdt-driver-dev"
RDEPENDS_${PN}-dev_append_tripledragon = " triple-sdk-dev"

PV = "0.1"

SRC_URI = " \
	file://COPYING.GPL \
"

do_install() {
	install -d ${D}/usr/bin
	cat >${D}/usr/bin/neutrino-dummy <<-EOF
	#!/bin/sh
	echo "this is not neutrino."
	EOF
	chmod 755 ${D}/usr/bin/neutrino-dummy
}
