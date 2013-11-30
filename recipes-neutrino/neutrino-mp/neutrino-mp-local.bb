####
#### this recipe is for building neutrino in order to deploy manually onto your target
#### no packages are built.
#### sources are taken from ${NEUTRINO_SOURCEDIR}
####
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
"

PREFERRED_PROVIDER_virtual/stb-hal-libs ?= "libstb-hal"

DEPENDS += " \
	virtual/stb-hal-libs \
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
	libvorbis \
	openthreads \
	lua5.2 \
	luaposix \
"

# set this to something further up the directory tree...
WORKDIR = "${TMPDIR}/neutrino-${MACHINE}"
NEUTRINO_SOURCEDIR ?= "/local/seife/src/neutrino-sources/neutrino-mp"
S = "${NEUTRINO_SOURCEDIR}"
B = "${WORKDIR}/build"

inherit autotools pkgconfig

include neutrino-mp.inc

do_compile () {
	test -e version.h || touch version.h
	# unset CFLAGS CXXFLAGS LDFLAGS
	oe_runmake CFLAGS="${N_CFLAGS}" CXXFLAGS="${N_CXXFLAGS}" LDFLAGS="${N_LDFLAGS}"
	# deliberately fail here, so that compile does not finish
	if [ $? == 0 ]; then
		echo "*******************************************************************"
		echo "*** this failure is not an error! *********************************"
		echo "*******************************************************************"
		echo "find your neutrino binary in"
		echo "${B}/src/neutrino"
		echo "*******************************************************************"
	fi
	false
}

PACKAGES = ""
RM_WORK_EXCLUDE += "${PN}"
