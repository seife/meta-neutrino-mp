# Base this image on core-image-minimal
include recipes-core/images/core-image-minimal.bb

# 488M + 20MB /boot +4MB alignment = 512M
IMAGE_ROOTFS_SIZE = "499712"

IMAGE_DEPENDS += " \
	e2fsprogs \
"

# all but not neutrino :-)
IMAGE_INSTALL += " \
	strace \
	procps \
	image-config \
	e2fsprogs-mke2fs \
	e2fsprogs-e2fsck \
	e2fsprogs-tune2fs \
	util-linux-blkid \
"

# Tripledragon Kernel is too old for udev
IMAGE_INSTALL += "${@'udev-extraconf' if MACHINE != 'tripledragon' else ''}"

# Include modules in rootfs, but not on coolstream, where
# flash is small...
IMAGE_INSTALL += "${@'kernel-modules' if MACHINE != 'coolstream' else ''}"

# we don't strictly need the kernel-image package, because kernel is packed extra
# for USB and flash images, but without it installed it is impossible to update later
IMAGE_INSTALL += "${@'kernel-image' if MACHINE != 'tripledragon' else ''}"

EXTRAOPKGCONFIG = "neutrino-feed-config"
