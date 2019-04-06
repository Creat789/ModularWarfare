package com.modularwarfare.common.network;

import com.modularwarfare.ModularWarfare;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketClientAnimation extends PacketBase {

	public PacketClientAnimation() { }
	
	/** Default Constructor */
	private AnimationType animType;
	public String wepType;
	
	public PacketClientAnimation(AnimationType animType, String wepType)
	{
		this.animType = animType;
		this.wepType = wepType;
	}
	
	/** Shoot Animation */
	public int fireDelay;
	public float recoilPitch;
	public float recoilYaw;
	
	public PacketClientAnimation(String wepType, int fireDelay, float recoilPitch, float recoilYaw)
	{
		this(AnimationType.Shoot, wepType);
		this.fireDelay = fireDelay;
		this.recoilPitch = recoilPitch;
		this.recoilYaw = recoilYaw;
	}
	
	/** Reload Animation */
	public int reloadTime;
	public int reloadCount;
	public int reloadType;
	
	public PacketClientAnimation(String wepType, int reloadTime, int reloadCount, int reloadType)
	{
		this(AnimationType.Reload, wepType);
		this.reloadTime = reloadTime;
		this.reloadCount = reloadCount;
		this.reloadType = reloadType;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
		data.writeByte(animType.i);
		writeUTF(data, wepType);
		
		switch(animType)
		{
		case Reload:
		{
			data.writeInt(reloadTime);
			data.writeInt(reloadCount);
			data.writeInt(reloadType);
			break;
		}
		case Shoot:
		{
			data.writeInt(fireDelay);
			data.writeFloat(recoilPitch);
			data.writeFloat(recoilYaw);
			break;
		}
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
		animType = AnimationType.getTypeFromInt(data.readByte());
		wepType = readUTF(data);
		
		switch(animType)
		{
		case Reload:
		{
			reloadTime = data.readInt();
			reloadCount = data.readInt();
			reloadType = data.readInt();
			break;
		}
		case Shoot:
		{
			fireDelay = data.readInt();
			recoilPitch = data.readFloat();
			recoilYaw = data.readFloat();
			break;
		}
		}
	}

	@Override
	public void handleServerSide(EntityPlayerMP playerEntity) {
		// This packet is client side only
	}

	@Override
	public void handleClientSide(EntityPlayer clientPlayer) {
		switch(animType)
		{
		case Reload:
		{
			ModularWarfare.PROXY.onReloadAnimation(clientPlayer, wepType, reloadTime, reloadCount, reloadType);
			break;
		}
		case Shoot:
		{
			ModularWarfare.PROXY.onShootAnimation(clientPlayer, wepType, fireDelay, recoilPitch, recoilYaw);
			break;
		}
		}
	}
	
	private enum AnimationType 
	{
		Shoot(0), Reload(1);
		
		public int i;
		AnimationType(int i)
		{
			this.i = i;
		}
		
		public static AnimationType getTypeFromInt(int i)
		{
			return i == 0 ? Shoot : Reload;
		}
	}

}
