package com.gabid.ezaciancraft.api.aspects;

import thaumcraft.api.aspects.IAspectContainer;

/// @api used for identify the machine what uses the extended AspectList system for my own custom render or other things...
public interface IExtendedAspectContainer extends IAspectContainer {
    ExtendedAspectList getExtendedAspects();
}
