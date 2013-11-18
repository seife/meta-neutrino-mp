require ffmpeg.inc

SRC_URI = "http://www.ffmpeg.org/releases/ffmpeg-2.0.2.tar.bz2"

SRC_URI[md5sum] = "6c5cfed204d8a108325d1fc439ab734a"
SRC_URI[sha256sum] = "986f63dc0785f473b1832d0eead502e39c9706fbe7f9677bbea0dad6fe0fab4a"

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
