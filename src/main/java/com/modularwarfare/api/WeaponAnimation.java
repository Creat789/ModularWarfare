package com.modularwarfare.api;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import com.modularwarfare.client.anim.AnimStateMachine;
import com.modularwarfare.client.anim.ReloadType;
import com.modularwarfare.client.anim.StateEntry;
import com.modularwarfare.client.anim.StateType;
import com.modularwarfare.client.anim.StateEntry.MathType;
import com.modularwarfare.client.model.ModelGun;

public class WeaponAnimation {
	
	public Vector3f ammoLoadOffset;
	
	public float tiltGunTime = 0.15F, unloadAmmoTime = 0.35F, loadAmmoTime = 0.35F, untiltGunTime = 0.15F;
	
	public void onGunAnimation(float reloadRotate, AnimStateMachine animation) 
	{
		
	}
	
	public void onAmmoAnimation(ModelGun gunModel, float ammoPosition, int reloadAmmoCount, AnimStateMachine animation)
	{
		
	}
	
	public ArrayList<StateEntry> getAnimStates(ReloadType reloadType, int reloadCount)
	{
		ArrayList<StateEntry> states = new ArrayList<StateEntry>();		
		states.add(new StateEntry(StateType.Tilt, 0.15f, 0.15f, 0f, MathType.Add));
		if(reloadType == ReloadType.Unload || reloadType == ReloadType.Full)
			states.add(new StateEntry(StateType.Unload, 0.35f, 0.50f, 0f, MathType.Add));
		if(reloadType == ReloadType.Load || reloadType == ReloadType.Full)
			states.add(new StateEntry(StateType.Load, 0.35f, 0.85f, 1f, MathType.Sub, reloadCount));
		states.add(new StateEntry(StateType.Untilt, 0.15f, 1f, 1f, MathType.Sub));
		return states;
	}
	
}
