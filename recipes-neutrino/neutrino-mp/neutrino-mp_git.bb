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

RCONFLICTS_${PN} = "neutrino-hd2"

#SRCREV = "c7a4927b53f674323931471b05b83a455a0e6506"
#SRCREV = "e92afd2b420f2e53cf45a79b29b9898df406fe2b"
SRCREV = "${AUTOREV}"
PV = "0.0+git${SRCPV}"
PR = "r18"

SRC_URI = " \
	git://gitorious.org/neutrino-mp/neutrino-mp.git;protocol=git \
	file://neutrino.init \
	file://timezone.xml \
	file://COPYING.GPL \
"

S = "${WORKDIR}/git"

inherit autotools pkgconfig update-rc.d 

INITSCRIPT_PACKAGES   = "${PN}"
INITSCRIPT_NAME_${PN} = "neutrino"
INITSCRIPT_PARAMS_${PN} = "start 99 5 2 . stop 20 0 1 6 ."


N_CFLAGS = "-Wall -W -Wshadow -g -O2 -fno-strict-aliasing -funsigned-char -rdynamic"
N_CXXFLAGS = "${N_CFLAGS}"
N_LDFLAGS += "-Wl,-rpath-link,${STAGING_DIR_HOST}/usr/lib"

N_CPPFLAGS = "-DDYNAMIC_LUAPOSIX"
CPPFLAGS = "${N_CPPFLAGS}"
CPPFLAGS_tripledragon += "${N_CPPFLAGS} -I${STAGING_DIR_HOST}/usr/include/hardware"

EXTRA_OECONF = " \
	--enable-maintainer-mode \
	--with-target=native \
	--disable-silent-rules \
	--with-tremor \
	--disable-upnp \
	--enable-giflib \
	--with-stb-hal-includes=${STAGING_DIR_HOST}/usr/include/libstb-hal \
"

EXTRA_OECONF_append_raspberrypi += "\
	--with-boxtype=generic --with-boxmodel=raspi \
"

EXTRA_OECONF_append_tripledragon += "\
	--with-boxtype=tripledragon \
"

EXTRA_OECONF_append_coolstream += "\
	--with-boxtype=coolstream \
"

EXTRA_OECONF_append_spark += "\
	--with-boxtype=spark \
"

EXTRA_OECONF_append_spark7162 += "\
	--with-boxtype=spark \
"

do_compile () {
	# unset CFLAGS CXXFLAGS LDFLAGS
	oe_runmake CFLAGS="${N_CFLAGS}" CXXFLAGS="${N_CXXFLAGS}" LDFLAGS="${N_LDFLAGS}"
}


do_install_prepend () {
	install -d ${D}/${sysconfdir}/init.d
	install -m 755 ${WORKDIR}/neutrino.init ${D}/${sysconfdir}/init.d/neutrino
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

pkg_prerm_${PN} () {
}

