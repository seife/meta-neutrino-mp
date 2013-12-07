SUMMARY = "Port of neutrino to multiple platforms, including (but not limited to) the Tripledragon, AZbox ME/miniME and Fulan Spark/Spark7162 settop boxes and the Raspberry Pi."
DESCRIPTION = "Port of neutrino to multiple platforms."
HOMEPAGE = "https://gitorious.org/neutrino-mp/neutrino-mp"
SECTION = "libs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING.GPL;md5=751419260aa954499f7abaabaa882bbe \
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

RDEPENDS_${PN} += " \
	tzdata \
"

# maybe this should rather be in the image dependencies? it is not needed for building...
DEPENDS_append_tripledragon = " \
	td-dvb-wrapper \
"
RDEPENDS_${PN}_append_tripledragon += "kernel-module-td-dvb-frontend"

RCONFLICTS_${PN} = "neutrino-hd2"

SRCREV = "${AUTOREV}"
PV = "0.0+git${SRCPV}"
PR = "r21"

SRC_URI = " \
	git://gitorious.org/neutrino-mp/neutrino-mp.git;protocol=git \
	file://neutrino.init \
	file://timezone.xml \
	file://custom-poweroff.init \
	file://COPYING.GPL \
"

S = "${WORKDIR}/git"

inherit autotools pkgconfig update-rc.d 

INITSCRIPT_PACKAGES   = "${PN}"
INITSCRIPT_NAME_${PN} = "neutrino"
INITSCRIPT_PARAMS_${PN} = "start 99 5 . stop 20 0 1 2 3 4 6 ."

include neutrino-mp.inc

do_configure_prepend() {
	INSTALL="`which install` -p"
	export INSTALL
}

do_compile () {
	# unset CFLAGS CXXFLAGS LDFLAGS
	oe_runmake CFLAGS="${N_CFLAGS}" CXXFLAGS="${N_CXXFLAGS}" LDFLAGS="${N_LDFLAGS}"
}


do_install_prepend () {
	install -d ${D}/${sysconfdir}/init.d
	install -m 755 ${WORKDIR}/neutrino.init ${D}/${sysconfdir}/init.d/neutrino
	install -m 755 ${WORKDIR}/custom-poweroff.init ${D}/${sysconfdir}/init.d/custom-poweroff
	install -m 644 ${WORKDIR}/timezone.xml ${D}/${sysconfdir}/timezone.xml
	install -d ${D}/share/tuxbox/neutrino/httpd-y
	install -d ${D}/share/tuxbox/neutrino/httpd
	install -d ${D}/share/fonts
	install -d ${D}/share/tuxbox/neutrino/icons
	install -d ${D}/var/cache
	install -d ${D}/var/tuxbox/config/
	install -d ${D}/var/tuxbox/plugins/
	echo "version=1200`date +%Y%m%d%H%M`"    > ${D}/.version 
	echo "creator=${MAINTAINER}"             >> ${D}/.version 
	echo "imagename=Neutrino-MP"             >> ${D}/.version 
	echo "homepage=${HOMEPAGE}"              >> ${D}/.version 
	update-rc.d -r ${D} custom-poweroff start 89 0 .
}

FILES_${PN} += "\
	/.version \
	${sysconfdir} \
	/usr/share \
	/usr/share/tuxbox \
	/usr/share/iso-codes \
	/usr/share/fonts \
	/usr/share/tuxbox/neutrino \
	/usr/share/iso-codes \
	/usr/share/fonts \
	/share/tuxbox/neutrino/httpd-y \
	/share/tuxbox/neutrino/httpd \
	/share/fonts \
	/share/tuxbox \
	/var/cache \
	/var/tuxbox/plugins \
"

pkg_postinst_${PN} () {
	update-alternatives --install /bin/backup.sh backup.sh /usr/bin/backup.sh 100
	update-alternatives --install /bin/install.sh install.sh /usr/bin/install.sh 100
	update-alternatives --install /bin/restore.sh restore.sh /usr/bin/restore.sh 100
	# pic2m2v is only available on platforms that use "real" libstb-hal
	if which pic2m2v >/dev/null 2>&1; then
		# neutrino icon path
		I=/usr/share/tuxbox/neutrino/icons
		pic2m2v $I/mp3.jpg $I/radiomode.jpg $I/scan.jpg $I/shutdown.jpg $I/start.jpg
	fi
}
