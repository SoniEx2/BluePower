package net.quetzi.bluepower.containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.quetzi.bluepower.containers.slots.SlotMachineInput;
import net.quetzi.bluepower.containers.slots.SlotMachineOutput;
import net.quetzi.bluepower.tileentities.tier1.TileAlloyFurnace;

public class ContainerAlloyFurnace extends Container
{
    private TileAlloyFurnace tileFurnace;

    private int currentBurnTime;
    private int maxBurnTime;

    public ContainerAlloyFurnace(InventoryPlayer invPlayer, TileAlloyFurnace furnace)
    {
        tileFurnace = furnace;

        addSlotToContainer(new SlotMachineInput(furnace, 0, 21, 35));
        addSlotToContainer(new SlotMachineOutput(furnace, 1, 134, 35));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                addSlotToContainer(new SlotMachineInput(furnace, (i * 3) + j + 2, 47 + (i * 18), 17 + (j * 18)));
            }
        }
        bindPlayerInventory(invPlayer);

    }

    protected void bindPlayerInventory(InventoryPlayer invPlayer)
    {
        //Render inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(invPlayer, j + (i * 9) + 9, 8 + (j * 18), 84 + (i * 18)));
            }
        }

        //Render hotbar
        for (int j = 0; j < 9; j++) {
            addSlotToContainer(new Slot(invPlayer, j, 8 + (j * 18), 142));
        }
    }

    // NOTE! This function does magic which i(K-4U) Do not completely understand.
    //Best ask MineMaarten to do this
    //TODO
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2){

        ItemStack var3 = null;
        Slot var4 = (Slot)inventorySlots.get(par2);

        if(var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if(par2 < 11) {
                if(!mergeItemStack(var5, 11, 47, false)) return null;
                var4.onSlotChange(var5, var3);
            } else {
                if(TileEntityFurnace.isItemFuel(var5) && mergeItemStack(var5, 0, 1, false)) {

                } else if(!mergeItemStack(var5, 2, 11, false)) return null;
                var4.onSlotChange(var5, var3);
            }

            if(var5.stackSize == 0) {
                var4.putStack((ItemStack)null);
            } else {
                var4.onSlotChanged();
            }

            if(var5.stackSize == var3.stackSize) return null;

            var4.onPickupFromSlot(par1EntityPlayer, var5);
        }

        return var3;
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (Object crafter : this.crafters)
        {
            ICrafting icrafting = (ICrafting) crafter;

            if (this.currentBurnTime != this.tileFurnace.currentBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.tileFurnace.currentBurnTime);
            }

            if (this.maxBurnTime != this.tileFurnace.maxBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 1, this.tileFurnace.maxBurnTime);
            }
        }

        this.currentBurnTime = this.tileFurnace.currentBurnTime;
        this.maxBurnTime = this.tileFurnace.maxBurnTime;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.tileFurnace.currentBurnTime = par2;
        }

        if (par1 == 1)
        {
            this.tileFurnace.maxBurnTime = par2;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return tileFurnace.isUseableByPlayer(entityplayer);
    }


}
