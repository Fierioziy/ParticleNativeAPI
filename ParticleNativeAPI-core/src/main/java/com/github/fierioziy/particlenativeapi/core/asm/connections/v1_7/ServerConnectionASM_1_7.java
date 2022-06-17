package com.github.fierioziy.particlenativeapi.core.asm.connections.v1_7;

import com.github.fierioziy.particlenativeapi.core.asm.ClassSkeletonImplement;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class ServerConnectionASM_1_7 extends ClassSkeletonImplement {

    protected Type playerConnTypeImpl = getTypeImpl(playerConnType);

    public ServerConnectionASM_1_7(InternalResolver resolver, String suffix) {
        super(resolver, serverConnType, suffix);
    }

    @Override
    protected void writeFields(ClassWriter cw) {

    }

    @Override
    protected void writeConstructor(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "<init>",
                "()V", null, null);
        mv.visitCode();

        int local_this = 0;

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitMethodInsn(INVOKESPECIAL,
                "java/lang/Object",
                "<init>",
                "()V", false);

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_createPlayerConnection(cw);

        // I really want them bo be separated
        // conditionals in ASM can be extremely hard to debug
        writeMethod_sendPacket_Player_Object(cw);
        writeMethod_sendPacket_Collection_Object(cw);
        writeMethod_sendPacketIf_Collection_Object_Predicate(cw);
        writeMethod_sendPacket_Location_Radius_Object(cw);
        writeMethod_sendPacketIf_Location_Radius_Object_Predicate(cw);
    }

    private void writeMethod_createPlayerConnection(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "createPlayerConnection",
                "(Lorg/bukkit/entity/Player;)" + playerConnType.getDescriptor(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_player = 1;

        // return new PlayerConnection_Impl(player);
        mv.visitTypeInsn(NEW, playerConnTypeImpl.getInternalName());
        mv.visitInsn(DUP);
        mv.visitVarInsn(ALOAD, local_player);
        mv.visitMethodInsn(INVOKESPECIAL,
                playerConnTypeImpl.getInternalName(),
                "<init>",
                "(Lorg/bukkit/entity/Player;)V", false);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private void writeMethod_sendPacket_Player_Object(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "sendPacket",
                "(Lorg/bukkit/entity/Player;Ljava/lang/Object;)V", null, null);
        mv.visitCode();

        int local_this = 0;
        int local_player = 1;
        int local_packet = 2;

        /*
        ((CraftPlayer) player).getHandle().playerConnection
                .sendPacket((Packet) packet);
         */
        mv.visitVarInsn(ALOAD, local_player);
        mv.visitTypeInsn(CHECKCAST, internalOBC("entity/CraftPlayer"));
        mv.visitMethodInsn(INVOKEVIRTUAL,
                internalOBC("entity/CraftPlayer"),
                "getHandle",
                "()" + descNMS("EntityPlayer"), false);

        mv.visitFieldInsn(GETFIELD,
                internalNMS("EntityPlayer"),
                "playerConnection",
                descNMS("PlayerConnection"));

        mv.visitVarInsn(ALOAD, local_packet);
        mv.visitTypeInsn(CHECKCAST, internalNMS("Packet"));

        mv.visitMethodInsn(INVOKEVIRTUAL,
                internalNMS("PlayerConnection"),
                "sendPacket",
                "(" + descNMS("Packet") + ")V", false);
        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private void writeMethod_sendPacket_Collection_Object(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "sendPacket",
                "(Ljava/util/Collection;Ljava/lang/Object;)V",
                "(Ljava/util/Collection<Lorg/bukkit/entity/Player;>;Ljava/lang/Object;)V", null);
        mv.visitCode();

        int local_this = 0;
        int local_players = 1;
        int local_packet = 2;
        int local_nmsPacket = 3;
        int local_length = 4;
        int local_it = 5;

        // Packet nmsPacket = (Packet) packet;
        mv.visitVarInsn(ALOAD, local_packet);
        mv.visitTypeInsn(CHECKCAST, internalNMS("Packet"));
        mv.visitVarInsn(ASTORE, local_nmsPacket);

        // int length = players.size();
        mv.visitVarInsn(ALOAD, local_players);
        mv.visitMethodInsn(INVOKEINTERFACE,
                "java/util/Collection",
                "size",
                "()I", true);
        mv.visitVarInsn(ISTORE, local_length);

        // Iterator it = players.iterator();
        mv.visitVarInsn(ALOAD, local_players);
        mv.visitMethodInsn(INVOKEINTERFACE,
                "java/util/Collection",
                "iterator",
                "()Ljava/util/Iterator;", true);
        mv.visitVarInsn(ASTORE, local_it);

        // while (
        Label loopBegin = new Label();
        Label loopEnd = new Label();

        // length > 0) {
        mv.visitLabel(loopBegin);
        mv.visitVarInsn(ILOAD, local_length);
        mv.visitJumpInsn(IFLE, loopEnd);

        // ((CraftPlayer) it.next()).getHandle()
        mv.visitVarInsn(ALOAD, local_it);
        mv.visitMethodInsn(INVOKEINTERFACE,
                "java/util/Iterator",
                "next",
                "()Ljava/lang/Object;", true);
        mv.visitTypeInsn(CHECKCAST, internalOBC("entity/CraftPlayer"));
        mv.visitMethodInsn(INVOKEVIRTUAL,
                internalOBC("entity/CraftPlayer"),
                "getHandle",
                "()" + descNMS("EntityPlayer"), false);

        // .playerConnection
        mv.visitFieldInsn(GETFIELD,
                internalNMS("EntityPlayer"),
                "playerConnection",
                descNMS("PlayerConnection"));

        // .sendPacket(nmsPacket);
        mv.visitVarInsn(ALOAD, local_nmsPacket);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                internalNMS("PlayerConnection"),
                "sendPacket",
                "(" + descNMS("Packet") + ")V", false);

        // --length;
        mv.visitIincInsn(local_length, -1);

        // }
        mv.visitJumpInsn(GOTO, loopBegin);
        mv.visitLabel(loopEnd);

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private void writeMethod_sendPacketIf_Collection_Object_Predicate(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "sendPacketIf",
                "(Ljava/util/Collection;Ljava/lang/Object;"
                        + playerPredicateType.getDescriptor() + ")V",
                "(Ljava/util/Collection<Lorg/bukkit/entity/Player;>;Ljava/lang/Object;"
                        + playerPredicateType.getDescriptor() + ")V", null);
        mv.visitCode();

        int local_this = 0;
        int local_players = 1;
        int local_packet = 2;
        int local_predicate = 3;
        int local_nmsPacket = 4;
        int local_size = 5;
        int local_it = 6;
        int local_player = 7;

        // Packet nmsPacket = (Packet) packet;
        mv.visitVarInsn(ALOAD, local_packet);
        mv.visitTypeInsn(CHECKCAST, internalNMS("Packet"));
        mv.visitVarInsn(ASTORE, local_nmsPacket);

        // int length = players.size();
        mv.visitVarInsn(ALOAD, local_players);
        mv.visitMethodInsn(INVOKEINTERFACE,
                "java/util/Collection",
                "size",
                "()I", true);
        mv.visitVarInsn(ISTORE, local_size);

        // Iterator it = players.iterator();
        mv.visitVarInsn(ALOAD, local_players);
        mv.visitMethodInsn(INVOKEINTERFACE,
                "java/util/Collection",
                "iterator",
                "()Ljava/util/Iterator;", true);
        mv.visitVarInsn(ASTORE, local_it);

        // while (
        Label loopBegin = new Label();
        Label loopEnd = new Label();

        // length > 0) {
        mv.visitLabel(loopBegin);
        mv.visitVarInsn(ILOAD, local_size);
        mv.visitJumpInsn(IFLE, loopEnd);

        // CraftPlayer p = ((CraftPlayer) it.next())
        mv.visitVarInsn(ALOAD, local_it);
        mv.visitMethodInsn(INVOKEINTERFACE,
                "java/util/Iterator",
                "next",
                "()Ljava/lang/Object;", true);
        mv.visitTypeInsn(CHECKCAST, internalOBC("entity/CraftPlayer"));
        mv.visitVarInsn(ASTORE, local_player);

        // if (predicate.shouldSend(p) {
        Label predicateCheckEnd = new Label();
        mv.visitVarInsn(ALOAD, local_predicate);
        mv.visitVarInsn(ALOAD, local_player);
        mv.visitMethodInsn(INVOKEINTERFACE,
                playerPredicateType.getInternalName(),
                "shouldSend",
                "(Lorg/bukkit/entity/Player;)Z", true);
        mv.visitJumpInsn(IFEQ, predicateCheckEnd);

        // player.getHandle()
        mv.visitVarInsn(ALOAD, local_player);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                internalOBC("entity/CraftPlayer"),
                "getHandle",
                "()" + descNMS("EntityPlayer"), false);

        // .playerConnection
        mv.visitFieldInsn(GETFIELD,
                internalNMS("EntityPlayer"),
                "playerConnection",
                descNMS("PlayerConnection"));

        // .sendPacket(nmsPacket);
        mv.visitVarInsn(ALOAD, local_nmsPacket);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                internalNMS("PlayerConnection"),
                "sendPacket",
                "(" + descNMS("Packet") + ")V", false);

        // }
        mv.visitLabel(predicateCheckEnd);

        // --length;
        mv.visitIincInsn(local_size, -1);
        mv.visitJumpInsn(GOTO, loopBegin);

        // }
        mv.visitLabel(loopEnd);

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private void writeMethod_sendPacket_Location_Radius_Object(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "sendPacket",
                "(Lorg/bukkit/Location;DLjava/lang/Object;)V", null, null);
        mv.visitCode();

        int local_this = 0;
        int local_loc = 1;
        int local_radius = 2;
        int local_packet = 4;
        int local_nmsPacket = 5;
        int local_x = 6;
        int local_y = 8;
        int local_z = 10;
        int local_length = 12;
        int local_it = 13;
        int local_p = 14;
        int local_pLoc = 15;

        // radius *= radius;
        mv.visitVarInsn(DLOAD, local_radius);
        mv.visitVarInsn(DLOAD, local_radius);
        mv.visitInsn(DMUL);
        mv.visitVarInsn(DSTORE, local_radius);

        // Packet nmsPacket = (Packet) packet;
        mv.visitVarInsn(ALOAD, local_packet);
        mv.visitTypeInsn(CHECKCAST, internalNMS("Packet"));
        mv.visitVarInsn(ASTORE, local_nmsPacket);

        // double x = loc.getX();
        mv.visitVarInsn(ALOAD, local_loc);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "org/bukkit/Location",
                "getX",
                "()D", false);
        mv.visitVarInsn(DSTORE, local_x);

        // double y = loc.getY();
        mv.visitVarInsn(ALOAD, local_loc);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "org/bukkit/Location",
                "getY",
                "()D", false);
        mv.visitVarInsn(DSTORE, local_y);

        // double z = loc.getZ();
        mv.visitVarInsn(ALOAD, local_loc);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "org/bukkit/Location",
                "getZ",
                "()D", false);
        mv.visitVarInsn(DSTORE, local_z);

        // 2x loc.getWorld().getPlayers()
        mv.visitVarInsn(ALOAD, local_loc);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "org/bukkit/Location",
                "getWorld",
                "()Lorg/bukkit/World;", false);
        mv.visitMethodInsn(INVOKEINTERFACE,
                "org/bukkit/World",
                "getPlayers",
                "()Ljava/util/List;", true);
        mv.visitInsn(DUP);

        // int length = players.size();
        mv.visitMethodInsn(INVOKEINTERFACE,
                "java/util/List",
                "size",
                "()I", true);
        mv.visitVarInsn(ISTORE, local_length);

        // Iterator it = players.iterator();
        mv.visitMethodInsn(INVOKEINTERFACE,
                "java/util/List",
                "iterator",
                "()Ljava/util/Iterator;", true);
        mv.visitVarInsn(ASTORE, local_it);

        // while )
        Label loopBegin = new Label();
        Label loopEnd = new Label();

        // length > 0) {
        mv.visitLabel(loopBegin);
        mv.visitVarInsn(ILOAD, local_length);
        mv.visitJumpInsn(IFLE, loopEnd);

        // CraftPlayer p = (CraftPlayer) it.next();
        mv.visitVarInsn(ALOAD, local_it);
        mv.visitMethodInsn(INVOKEINTERFACE,
                "java/util/Iterator",
                "next",
                "()Ljava/lang/Object;", true);
        mv.visitTypeInsn(CHECKCAST, internalOBC("entity/CraftPlayer"));
        mv.visitVarInsn(ASTORE, local_p);

        // Location pLoc = p.getLocation();
        mv.visitVarInsn(ALOAD, local_p);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                internalOBC("entity/CraftPlayer"),
                "getLocation",
                "()Lorg/bukkit/Location;", false);
        mv.visitVarInsn(ASTORE, local_pLoc);

        // radius if statement
        // (pLoc.getX() - x)^2
        mv.visitVarInsn(ALOAD, local_pLoc);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "org/bukkit/Location",
                "getX",
                "()D", false);
        mv.visitVarInsn(DLOAD, local_x);
        mv.visitInsn(DSUB);
        mv.visitInsn(DUP2);
        mv.visitInsn(DMUL);

        // + (pLoc.getY() - y)^2
        mv.visitVarInsn(ALOAD, local_pLoc);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "org/bukkit/Location",
                "getY",
                "()D", false);
        mv.visitVarInsn(DLOAD, local_y);
        mv.visitInsn(DSUB);
        mv.visitInsn(DUP2);
        mv.visitInsn(DMUL);

        mv.visitInsn(DADD);

        // + (pLoc.getZ() - z)^2)
        mv.visitVarInsn(ALOAD, local_pLoc);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "org/bukkit/Location",
                "getZ",
                "()D", false);
        mv.visitVarInsn(DLOAD, local_z);
        mv.visitInsn(DSUB);
        mv.visitInsn(DUP2);
        mv.visitInsn(DMUL);

        mv.visitInsn(DADD);

        // if (distSquared <= radius) {
        mv.visitVarInsn(DLOAD, local_radius);
        Label notSendLabel = new Label();
        mv.visitInsn(DCMPL);
        mv.visitJumpInsn(IFGT, notSendLabel);

        // p.getHandle().playerConnection
        mv.visitVarInsn(ALOAD, local_p);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                internalOBC("entity/CraftPlayer"),
                "getHandle",
                "()" + descNMS("EntityPlayer"), false);
        mv.visitFieldInsn(GETFIELD,
                internalNMS("EntityPlayer"),
                "playerConnection",
                descNMS("PlayerConnection"));

        // .sendPacket(packet);
        mv.visitVarInsn(ALOAD, local_nmsPacket);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                internalNMS("PlayerConnection"),
                "sendPacket",
                "(" + descNMS("Packet") + ")V", false);

        // }
        mv.visitLabel(notSendLabel);

        // --length;
        mv.visitIincInsn(local_length, -1);

        // }
        mv.visitJumpInsn(GOTO, loopBegin);
        mv.visitLabel(loopEnd);

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private void writeMethod_sendPacketIf_Location_Radius_Object_Predicate(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "sendPacketIf",
                "(Lorg/bukkit/Location;DLjava/lang/Object;"
                        + playerPredicateType.getDescriptor() + ")V", null, null);
        mv.visitCode();

        int local_this = 0;
        int local_loc = 1;
        int local_radius = 2;
        int local_packet = 4;
        int local_predicate = 5;
        int local_nmsPacket = 6;
        int local_x = 7;
        int local_y = 9;
        int local_z = 11;
        int local_length = 13;
        int local_it = 14;
        int local_p = 15;
        int local_pLoc = 16;

        // radius *= radius;
        mv.visitVarInsn(DLOAD, local_radius);
        mv.visitVarInsn(DLOAD, local_radius);
        mv.visitInsn(DMUL);
        mv.visitVarInsn(DSTORE, local_radius);

        // Packet nmsPacket = (Packet) packet;
        mv.visitVarInsn(ALOAD, local_packet);
        mv.visitTypeInsn(CHECKCAST, internalNMS("Packet"));
        mv.visitVarInsn(ASTORE, local_nmsPacket);

        // double x = loc.getX();
        mv.visitVarInsn(ALOAD, local_loc);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "org/bukkit/Location",
                "getX",
                "()D", false);
        mv.visitVarInsn(DSTORE, local_x);

        // double y = loc.getY();
        mv.visitVarInsn(ALOAD, local_loc);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "org/bukkit/Location",
                "getY",
                "()D", false);
        mv.visitVarInsn(DSTORE, local_y);

        // double z = loc.getZ();
        mv.visitVarInsn(ALOAD, local_loc);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "org/bukkit/Location",
                "getZ",
                "()D", false);
        mv.visitVarInsn(DSTORE, local_z);

        // 2x loc.getWorld().getPlayers()
        mv.visitVarInsn(ALOAD, local_loc);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "org/bukkit/Location",
                "getWorld",
                "()Lorg/bukkit/World;", false);
        mv.visitMethodInsn(INVOKEINTERFACE,
                "org/bukkit/World",
                "getPlayers",
                "()Ljava/util/List;", true);
        mv.visitInsn(DUP);

        // int length = players.size();
        mv.visitMethodInsn(INVOKEINTERFACE,
                "java/util/List",
                "size",
                "()I", true);
        mv.visitVarInsn(ISTORE, local_length);

        // Iterator it = players.iterator();
        mv.visitMethodInsn(INVOKEINTERFACE,
                "java/util/List",
                "iterator",
                "()Ljava/util/Iterator;", true);
        mv.visitVarInsn(ASTORE, local_it);

        // while )
        Label loopBegin = new Label();
        Label loopEnd = new Label();

        // length > 0) {
        mv.visitLabel(loopBegin);
        mv.visitVarInsn(ILOAD, local_length);
        mv.visitJumpInsn(IFLE, loopEnd);

        // CraftPlayer p = (CraftPlayer) it.next();
        mv.visitVarInsn(ALOAD, local_it);
        mv.visitMethodInsn(INVOKEINTERFACE,
                "java/util/Iterator",
                "next",
                "()Ljava/lang/Object;", true);
        mv.visitTypeInsn(CHECKCAST, internalOBC("entity/CraftPlayer"));
        mv.visitVarInsn(ASTORE, local_p);

        // Location pLoc = p.getLocation();
        mv.visitVarInsn(ALOAD, local_p);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                internalOBC("entity/CraftPlayer"),
                "getLocation",
                "()Lorg/bukkit/Location;", false);
        mv.visitVarInsn(ASTORE, local_pLoc);

        // radius if statement
        // (pLoc.getX() - x)^2
        mv.visitVarInsn(ALOAD, local_pLoc);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "org/bukkit/Location",
                "getX",
                "()D", false);
        mv.visitVarInsn(DLOAD, local_x);
        mv.visitInsn(DSUB);
        mv.visitInsn(DUP2);
        mv.visitInsn(DMUL);

        // + (pLoc.getY() - y)^2
        mv.visitVarInsn(ALOAD, local_pLoc);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "org/bukkit/Location",
                "getY",
                "()D", false);
        mv.visitVarInsn(DLOAD, local_y);
        mv.visitInsn(DSUB);
        mv.visitInsn(DUP2);
        mv.visitInsn(DMUL);

        mv.visitInsn(DADD);

        // + (pLoc.getZ() - z)^2)
        mv.visitVarInsn(ALOAD, local_pLoc);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "org/bukkit/Location",
                "getZ",
                "()D", false);
        mv.visitVarInsn(DLOAD, local_z);
        mv.visitInsn(DSUB);
        mv.visitInsn(DUP2);
        mv.visitInsn(DMUL);

        mv.visitInsn(DADD);

        // if (distSquared <= radius && predicate.shouldSend(p)) {
        mv.visitVarInsn(DLOAD, local_radius);
        Label notSendLabel = new Label();
        mv.visitInsn(DCMPL);
        mv.visitJumpInsn(IFGT, notSendLabel);

        mv.visitVarInsn(ALOAD, local_predicate);
        mv.visitVarInsn(ALOAD, local_p);
        mv.visitMethodInsn(INVOKEINTERFACE,
                playerPredicateType.getInternalName(),
                "shouldSend",
                "(Lorg/bukkit/entity/Player;)Z", true);

        mv.visitJumpInsn(IFEQ, notSendLabel);

        // p.getHandle().playerConnection
        mv.visitVarInsn(ALOAD, local_p);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                internalOBC("entity/CraftPlayer"),
                "getHandle",
                "()" + descNMS("EntityPlayer"), false);
        mv.visitFieldInsn(GETFIELD,
                internalNMS("EntityPlayer"),
                "playerConnection",
                descNMS("PlayerConnection"));

        // .sendPacket(packet);
        mv.visitVarInsn(ALOAD, local_nmsPacket);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                internalNMS("PlayerConnection"),
                "sendPacket",
                "(" + descNMS("Packet") + ")V", false);

        // }
        mv.visitLabel(notSendLabel);

        // --length;
        mv.visitIincInsn(local_length, -1);

        // }
        mv.visitJumpInsn(GOTO, loopBegin);
        mv.visitLabel(loopEnd);

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
