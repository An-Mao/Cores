package dev.anye.mc.cores.helper.codec;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class Codecs {
	public static final PrimitiveCodec<byte[]> BYTE = new PrimitiveCodec<>() {
		@Override
		public <T> DataResult<byte[]> read(final DynamicOps<T> ops, final T input) {
			return ops.getByteBuffer(input).map(ByteBuffer::array);
		}

		@Override
		public <T> T write(final DynamicOps<T> ops, final byte[] value) {
			return ops.createByteList(ByteBuffer.wrap(value));
		}

		@Override
		public String toString() {
			return "byteArray";
		}
	};


	public interface CanCodec extends Serializable {
		byte[] getBytes();
	}

    /*
    public static <T extends CanCodec> Codec<T> codec(Class<T> obj){
        return BYTE.xmap(obj.newInstance(), obj.getBytes());
    }

     */

}
