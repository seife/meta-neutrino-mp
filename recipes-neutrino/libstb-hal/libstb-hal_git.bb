##
## TODO: spark rules are not tested
##

SUMMARY = "Library to abstract STB hardware. Supports Tripledragon, AZbox ME, Fulan Spark boxes as well as generic PC hardware and the Raspberry Pi right now."
DESCRIPTION = "Library to abstract STB hardware."
HOMEPAGE = "https://gitorious.org/neutrino-hd/libstb-hal"
SECTION = "libs"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING.GPL;md5=751419260aa954499f7abaabaa882bbe"

# hack: make sure we do not try to build on coolstream
COMPATIBLE_MACHINE_coolstream = "none"

DEPENDS = "\
	openthreads \
	ffmpeg \
"

# on coolstream, the same is provided by cs-drivers pkg (libcoolstream)
PROVIDES += "virtual/stb-hal-libs"

DEPENDS_append_spark = "tdt-driver libass"
DEPENDS_append_spark7162 = "tdt-driver libass"
DEPENDS_append_raspberrypi = "virtual/egl"

RDEPENDS_${PN} = "ffmpeg"

SRCREV = "${AUTOREV}"
PV = "0.0+git${SRCPV}"
PR = "r6.1"

PACKAGES_spark += "spark-fp"
PACKAGES_spark7162 += "spark-fp"

SRC_URI = " \
            git://gitorious.org/neutrino-hd/libstb-hal.git;protocol=git \
            file://COPYING.GPL \
"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

CFLAGS_append = " -Wall -W -Wshadow -g -O2 -fno-strict-aliasing -rdynamic -DNEW_LIBCURL"

SPARK_GEN_CFLAGS = "-funsigned-char"

CFLAGS_spark += "${SPARK_GEN_CFLAGS} "
CFLAGS_spark7162 += "${SPARK_GEN_CFLAGS} "


LDFLAGS = " -Wl,-rpath-link,${STAGING_DIR_HOST}/usr/lib -L${STAGING_DIR_HOST}/usr/lib"

EXTRA_OECONF += "\
	--enable-maintainer-mode \
	--disable-silent-rules \
	--enable-shared \
"

SPARK_GEN_EXTRA_OECONF = " --with-boxtype=spark "

EXTRA_OECONF_append_spark += "${SPARK_GEN_EXTRA_OECONF}"
EXTRA_OECONF_append_spark7162 += "${SPARK_GEN_EXTRA_OECONF}"
EXTRA_OECONF_append_raspberrypi += "--with-boxtype=generic --with-boxmodel=raspi"

FILES_${PN} = "\
	${libdir}/* \
	${bindir}/libstb-hal-test \
	${bindir}/pic2m2v \
"
FILES_spark_${PN} = "${bindir}/meta ${bindir}/eplayer3"
FILES_spark7162_${PN} = "${bindir}/meta ${bindir}/eplayer3"

FILES_${PN}-dev += "${includedir}/libstb-hal/*"

FILES_spark-fp = "${bindir}/spark_fp"
