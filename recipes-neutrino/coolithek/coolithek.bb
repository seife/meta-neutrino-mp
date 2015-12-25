DESCRIPTION = "Mediathek Lua plugin"
AUTHOR = "Michael Liebmann"
LICENSE = "GPL-2.0+"

PACKAGE_ARCH = "all"

LIC_FILES_CHKSUM = " \
	file://var/tuxbox/plugins/coolithek/COPYING;md5=751419260aa954499f7abaabaa882bbe \
"

PV = "0.2beta-8_2015-12-08"
PR = "r0"

### the .bin file which is distributed is actually a .tar.gz...
SRC_URI = " \
	file://coolithekV${PV}.tar.gz \
"

S = "${WORKDIR}/temp_inst/inst"

do_install () {
	install -d ${D}/${libdir}/tuxbox/
	install -d ${D}/${datadir}/lua/5.2
	cp -r --preserve=timestamps ${S}/var/tuxbox/ ${D}/${libdir}/
	cp -r --preserve=timestamps ${S}/share/lua   ${D}/${datadir}/
}

FILES_${PN} = " \
	${libdir}/tuxbox/plugins \
	${datadir}/lua/5.2 \
"

pkg_postinst_${PN} () {
	# pic2m2v is only available on platforms that use "real" libstb-hal
	if which pic2m2v >/dev/null 2>&1; then
		pic2m2v ${libdir}/tuxbox/plugins/coolithek/background.jpg
	fi
}

