DESCRIPTION = "tuxbox plugins, ported to neutrino-hd \
"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://tuxcom/tuxcom.c;beginline=10;endline=24;md5=8cfd78763de33face1d26b11904e84d5"
HOMEPAGE = "https://gitorious.org/neutrino-hd/neutrino-hd-plugins"

DEPENDS = "freetype"

SRCREV = "${AUTOREV}"
PV = "0.0+git${SRCPV}"
# does not work like that?
# PV_nhd-plugin-tuxcom = "1.17+git${SRCPV}"

SRC_URI = "git://github.com/neutrino-mp/neutrino-hd-plugins.git;protocol=http \
"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

EXTRA_OECONF += " \
	--enable-maintainer-mode \
	--with-target=native \
	--with-configdir=/var/tuxbox/config \
"

EXTRA_OECONF_append_spark = "--with-boxtype=spark"
EXTRA_OECONF_append_coolstream = "--with-boxtype=coolstream"
EXTRA_OECONF_append_tripledragon = "--with-boxtype=tripledragon"

N_CFLAGS = "-Wall -W -Wshadow -g -O2 -funsigned-char"
N_CXXFLAGS = "${N_CFLAGS}"
N_LDFLAGS += "-Wl,-rpath-link,${STAGING_DIR_HOST}${libdir}"

PLUGINS_TO_BUILD = "tuxcom"

do_compile () {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
	oe_runmake CFLAGS="${N_CFLAGS}" CXXFLAGS="${N_CXXFLAGS}" LDFLAGS="${N_LDFLAGS}" SUBDIRS="${PLUGINS_TO_BUILD}"
}


do_install () {
	for i in ${PLUGINS_TO_BUILD}; do
		oe_runmake install SUBDIRS="$i" DESTDIR=${D}
	done
}

PACKAGES += "nhd-plugin-tuxcom"

# main package is empty, might later depend on all plugins
FILES_${PN} = ""
FILES_${PN}-dbg += "${libdir}/tuxbox/plugins/.debug"

FILES_nhd-plugin-tuxcom = " \
	${libdir}/tuxbox/plugins/tuxcom* \
	/var/tuxbox/config/tuxcom/ \
"
