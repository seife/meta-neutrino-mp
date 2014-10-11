PV = "2.3.3"

require ffmpeg.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/ffmpeg-2.3:"

SRC_URI = " \
	http://www.ffmpeg.org/releases/ffmpeg-${PV}.tar.bz2;name=ffmpeg-${PV} \
	file://0001-avformat-vobsub-fix-NULL-dereference.patch \
	file://0002-ffmpeg-add-ASF-VC1-Annex-G-and-RCV-bitstream-filters.patch \
	file://0003-libavutil-libm.h-fix-compile.patch \
	file://0005-libavformat-aviobuf-keep-track-of-the-original-buffe.patch \
	file://0006-Add-HDS-support.patch \
	file://0007-Revert-h264_mp4toannexb_bsf-account-for-consecutive-.patch \
	file://0008-Revert-avcodec-h264_mp4toannexb_bsf-fix-issue-when-s.patch \
	file://0009-libavformat-utils.c-fix-linking-issue-that-breaks-ff.patch \
"

SRC_URI[ffmpeg-2.3.3.md5sum] = "72361d3b8717b6db3ad2b9da8df7af5e"
SRC_URI[ffmpeg-2.3.3.sha256sum] = "bb4c0d10a24e08fe67292690a1b4d4ded04f5c4c388f0656c98940ab0c606446"

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
