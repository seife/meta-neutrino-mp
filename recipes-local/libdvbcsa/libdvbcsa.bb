SUMMARY = "DVB Common Scrambling Algorithm with encryption and decryption capabilities"
DESCRIPTION = "libdvbcsa is a free implementation of the DVB Common Scrambling Algorithm - DVB/CSA - with encryption and decryption capabilities."
HOMEPAGE = "http://www.videolan.org/developers/libdvbcsa.html"
SECTION = "libs/multimedia"

PV = "1.1.0"

LICENSE = "GPLv2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

SRC_URI = "http://download.videolan.org/pub/videolan/libdvbcsa/${PV}/${PN}-${PV}.tar.gz \
	file://0001-Use-void-where-applicable.patch \
	file://0002-removed-use-of-deprecated-AM_CONFIG_HEADER-macro-in-.patch \
	file://0003-added-missing-inline-keywords.patch \
	file://0004-use-integer-sse2-operations-instead-of-single-precis.patch \
	file://0005-reduced-arrays-size-in-bitslice-stream-cipher.patch \
	file://0006-replaced-bytes-array-cast-by-union-in-bitslice-block.patch \
	file://0007-removed-BS_XOREQ-macro-from-bitslice-ops-headers.patch \
	file://0008-Fix-C-compilation-using-the-library.patch \
"
# double checked against http://download.videolan.org/videolan/libbluray/0.5.0/libbluray-0.5.0.tar.bz2.sha512
SRC_URI[md5sum] = "478ab1ca56ca58d2667da6ce22f74e39"
SRC_URI[sha256sum] = "4db78af5cdb2641dfb1136fe3531960a477c9e3e3b6ba19a2754d046af3f456d"

inherit autotools lib_package pkgconfig

#EXTRA_OECONF = "--without-libxml2"
