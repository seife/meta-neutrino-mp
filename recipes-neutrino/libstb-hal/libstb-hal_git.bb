##
## TODO: spark rules are only build tested
##

SUMMARY = "Library to abstract STB hardware. Supports Tripledragon, AZbox ME, Fulan Spark boxes as well as generic PC hardware and the Raspberry Pi right now."
DESCRIPTION = "Library to abstract STB hardware."
HOMEPAGE = "https://gitorious.org/neutrino-hd/libstb-hal"
SECTION = "libs"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://${THISDIR}/libstb-hal/COPYING.GPL;md5=751419260aa954499f7abaabaa882bbe"

# hack: make sure we do not try to build on coolstream
COMPATIBLE_MACHINE_coolstream = "none"
#
# this stuff really is machine specific, not CPU specific
PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "\
	openthreads \
	ffmpeg \
"

# on coolstream, the same is provided by cs-drivers pkg (libcoolstream)
PROVIDES += "virtual/stb-hal-libs"

DEPENDS_append_spark = "tdt-driver libass"
DEPENDS_append_raspberrypi = "virtual/egl"
DEPENDS_append_tripledragon = "directfb triple-sdk"

RDEPENDS_${PN} = "ffmpeg"

SRCREV = "${AUTOREV}"
PV = "0.0+git${SRCPV}"
PR = "r9"

# prepend, or it will end up in -bin package...
PACKAGES_prepend_spark = "spark-fp "
# libstb-hal-bin package for testing binaries etc.
PACKAGES += "${PN}-bin"

SRC_URI = " \
	git://gitorious.org/neutrino-hd/libstb-hal.git;protocol=git \
	file://blank_480.mpg \
	file://blank_576.mpg \
"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

# CFLAGS_append = " -Wall -W -Wshadow -g -O2 -fno-strict-aliasing -rdynamic -DNEW_LIBCURL"

CFLAGS_spark += "-funsigned-char"
CPPFLAGS_tripledragon += "-I${STAGING_DIR_HOST}/usr/include/hardware"

LDFLAGS = " -Wl,-rpath-link,${STAGING_DIR_HOST}/usr/lib -L${STAGING_DIR_HOST}/usr/lib"

EXTRA_OECONF += "\
	--enable-maintainer-mode \
	--disable-silent-rules \
	--enable-shared \
"

EXTRA_OECONF_append_spark += "--with-boxtype=spark"
EXTRA_OECONF_append_raspberrypi += "--with-boxtype=generic --with-boxmodel=raspi"
EXTRA_OECONF_append_tripledragon += "--with-boxtype=tripledragon"

do_install_append() {
	install -d ${D}/${datadir}
}

do_install_append_tripledragon() {
	install -D -m 0644 ${WORKDIR}/blank_576.mpg ${D}/${datadir}/tuxbox/blank_576.mpg
	install -D -m 0644 ${WORKDIR}/blank_480.mpg ${D}/${datadir}/tuxbox/blank_480.mpg
}

# pic2m2v is included in lib package, because it is always needed,
# libstb-hal-bin contains all other binaries, which are rather for testing only
FILES_${PN} = "\
	${libdir}/* \
	${bindir}/pic2m2v \
	${datadir} \
"

FILES_${PN}-dev += "${includedir}/libstb-hal/*"

FILES_spark-fp = "${bindir}/spark_fp"
