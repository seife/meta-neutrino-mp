DESCRIPTION = "libass is a portable subtitle renderer for the ASS/SSA (Advanced Substation Alpha/Substation Alpha) subtitle format. It is mostly compatible with VSFilter."
HOMEPAGE = "http://code.google.com/p/libass/"
SECTION = "libs/multimedia"

LICENSE = "BSD-3-Clause"

DEPENDS = "enca fontconfig freetype libpng fribidi"

SRC_URI = "http://storage.googleapis.com/google-code-archive-downloads/v2/code.google.com/${PN}/${P}.tar.xz"

inherit autotools pkgconfig

PR="r1"

EXTRA_OECONF = " \
	--enable-enca \
	--enable-fontconfig \
	--disable-harfbuzz \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=8ae98663bac55afe5d989919d296f28a"

SRC_URI[md5sum] = "ce672ed5629c9708b3401b976f516744"
SRC_URI[sha256sum] = "f02afcc6410b800f0007dc7c282e897dab64f817c23b37d171fd6ff7fc4ca1d8"
