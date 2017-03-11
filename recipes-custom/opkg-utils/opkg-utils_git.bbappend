###
### this fork has update-alternatives implemented in C
###
FILESEXTRAPATHS_prepend := "${THISDIR}/opkg-utils:"
SRC_URI = " \
	git://github.com/seife/opkg-utils;protocol=http \
"
SRCREV = "master"
PR .= ".1"
