SUMMARY = "Library to access Blu-Ray disks"
DESCRIPTION = "libbluray is an open-source library designed for Blu-Ray Discs playback for media players"
HOMEPAGE = "http://www.videolan.org/developers/libbluray.html"
SECTION = "libs/multimedia"

PV = "0.5.0"

LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=435ed639f84d4585d93824e7da3d85da"
DEPENDS = "libxml2 freetype"

SRC_URI = "http://download.videolan.org/videolan/libbluray/${PV}/libbluray-${PV}.tar.bz2 \
	file://0001-m2ts_filter-reduced-logging.patch \
	file://0001-Optimized-file-I-O-for-chained-usage-with-libavforma.patch \
	file://0002-Added-bd_get_clip_infos.patch \
	file://0003-Don-t-abort-demuxing-if-the-disc-looks-encrypted.patch \
"
# double checked against http://download.videolan.org/videolan/libbluray/0.5.0/libbluray-0.5.0.tar.bz2.sha512
SRC_URI[md5sum] = "04cf15d0f3581a955d3a2ccb9dc06e9e"
SRC_URI[sha256sum] = "19213e193e1abc5ed2de65edfb539deda1dbf6cc64dfef03e405524c9c6f7216"

inherit autotools lib_package pkgconfig

