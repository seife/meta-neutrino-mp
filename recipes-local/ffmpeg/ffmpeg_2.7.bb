PV = "2.7.4"

require ffmpeg.inc

PR = "${INC_PR}"

FILESEXTRAPATHS_prepend := "${THISDIR}/ffmpeg-2.7:"

SRC_URI = " \
	http://www.ffmpeg.org/releases/ffmpeg-${PV}.tar.bz2;name=ffmpeg-${PV} \
"

SRC_URI_append = " \
	file://0002-ffmpeg-add-ASF-VC1-Annex-G-and-RCV-bitstream-filters.patch \
	file://0005-libavformat-aviobuf-keep-track-of-the-original-buffe.patch \
	file://0006-Add-HDS-support.patch \
	file://0009-libavformat-utils.c-fix-linking-issue-that-breaks-ff.patch \
"
# newer patches ffmpeg-head => dvbsubfix
SRC_URI_append = " \
	file://0002-avcodec-dvbsubdec-Implement-display-definition-segme.patch \
	file://0003-avcodec-dvbsubdec-Compute-default-CLUT-based-on-bitm.patch \
	file://0004-avcodec-dvbsubdec-Allow-selecting-the-substream-or-a.patch \
	file://0005-ffplay-Use-sws_scale-to-scale-subtitles.patch \
	file://0006-avcodec-dvbsubdec-Add-option-to-select-when-to-compu.patch \
"


SRC_URI[ffmpeg-2.7.4.md5sum] = "86e3238deb5aa77eee772c8b88d0c3cb"
SRC_URI[ffmpeg-2.7.4.sha256sum] = "cbc780e05c37db4fb3b81887293519a29ad10366ad63c56495863af1aaad5155"

LIC_FILES_CHKSUM = " \
	file://COPYING.GPLv2;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
	file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504 \
	file://COPYING.LGPLv2.1;md5=bd7a443320af8c812e4c18d1b79df004 \
	file://COPYING.LGPLv3;md5=e6a600fd5e1d9cbde2d983680233ad02 \
"

EXTRA_OECONF += " \
	--enable-postproc \
"

PROVIDES = "ffmpeg_${PV}"
