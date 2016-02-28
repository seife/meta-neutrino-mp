SUMMARY = "SIMD-accelerated libjpeg-compatible JPEG codec library"
DESCRIPTION = "libjpeg-turbo is a derivative of libjpeg that uses SIMD instructions (MMX, SSE2, NEON) to accelerate baseline JPEG compression and decompression"
HOMEPAGE = "http://libjpeg-turbo.org/"

PV = "1.4.2"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = " \
	file://cdjpeg.h;endline=12;md5=cad955d15145c3fdceec6855e078e953 \
	file://jpeglib.h;endline=16;md5=e54e2454726e618531fa97abec6395cf \
	file://djpeg.c;endline=13;md5=7287e50af9f6680ca2da445cbef9ba31 \
"

SRC_URI = "http://sourceforge.net/projects/libjpeg-turbo/files/${PV}/libjpeg-turbo-${PV}.tar.gz"

SRC_URI[md5sum] = "86b0d5f7507c2e6c21c00219162c3c44"
SRC_URI[sha256sum] = "521bb5d3043e7ac063ce3026d9a59cc2ab2e9636c655a2515af5f4706122233e"

# Drop-in replacement for jpeg
PROVIDES = "jpeg"
RPROVIDES_${PN} += "jpeg"
RREPLACES_${PN} += "jpeg"
RCONFLICTS_${PN} += "jpeg"

inherit autotools pkgconfig

# EXTRA_OECONF = "--with-jpeg8 "

PACKAGES =+ "jpeg-tools libturbojpeg"

DESCRIPTION_jpeg-tools = "The jpeg-tools package includes the client programs for access libjpeg functionality.  These tools allow for the compression, decompression, transformation and display of JPEG files."
FILES_jpeg-tools = "${bindir}/*"

FILES_libturbojpeg = "${libdir}/libturbojpeg.so.*"

BBCLASSEXTEND = "native"
DEPENDS = "nasm-native"
