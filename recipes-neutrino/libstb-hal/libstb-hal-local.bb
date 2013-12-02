####
#### this recipe is for building libstb-hal in order to link it statically with neutrino-mp-local
#### no packages are built.
#### sources are taken from ${LIBSTBHAL_SOURCEDIR}
####
#### spark stuff is totally untested
####
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://${THISDIR}/libstb-hal/COPYING.GPL;md5=751419260aa954499f7abaabaa882bbe"

# hack: make sure we do not try to build on coolstream
COMPATIBLE_MACHINE_coolstream = "none"

DEPENDS = "\
	openthreads \
	ffmpeg \
"

DEPENDS_append_spark = "tdt-driver libass"
DEPENDS_append_spark7162 = "tdt-driver libass"
DEPENDS_append_raspberrypi = "virtual/egl"
DEPENDS_append_tripledragon = "directfb triple-sdk"

WORKDIR = "${TMPDIR}/neutrino-${MACHINE}"
LIBSTBHAL_SOURCEDIR ?= "/local/seife/src/neutrino-sources/libstb-hal"
S = "${LIBSTBHAL_SOURCEDIR}"
B = "${WORKDIR}/hal-build"

inherit autotools pkgconfig

# CFLAGS_append = " -Wall -W -Wshadow -g -O2 -fno-strict-aliasing -rdynamic -DNEW_LIBCURL"

SPARK_GEN_CFLAGS = "-funsigned-char"

CFLAGS_spark += "${SPARK_GEN_CFLAGS} "
CFLAGS_spark7162 += "${SPARK_GEN_CFLAGS} "
CPPFLAGS_tripledragon += "-I${STAGING_DIR_HOST}/usr/include/hardware"

LDFLAGS = " -Wl,-rpath-link,${STAGING_DIR_HOST}/usr/lib -L${STAGING_DIR_HOST}/usr/lib"

EXTRA_OECONF += "\
	--enable-maintainer-mode \
	--disable-silent-rules \
	--disable-shared \
	--enable-static \
"

SPARK_GEN_EXTRA_OECONF = " --with-boxtype=spark "

EXTRA_OECONF_append_spark += "${SPARK_GEN_EXTRA_OECONF}"
EXTRA_OECONF_append_spark7162 += "${SPARK_GEN_EXTRA_OECONF}"
EXTRA_OECONF_append_raspberrypi += "--with-boxtype=generic --with-boxmodel=raspi"
EXTRA_OECONF_append_tripledragon += "--with-boxtype=tripledragon"

do_compile () {
	test -e version.h || touch version.h
	# unset CFLAGS CXXFLAGS LDFLAGS
	oe_runmake CFLAGS="${N_CFLAGS}" CXXFLAGS="${N_CXXFLAGS}" LDFLAGS="${N_LDFLAGS}"
	oe_runmake install DESTDIR="${B}/dest" # the headers are used from there
	# deliberately fail here, so that compile does not finish
	if [ $? == 0 ]; then
		echo "*******************************************************************"
		echo "*** this failure is not an error! *********************************"
		echo "*******************************************************************"
	fi
	false
}

PACKAGES = ""
RM_WORK_EXCLUDE += "${PN}"
