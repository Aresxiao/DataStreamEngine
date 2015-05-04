package game.sharedmemory.message;

import java.util.Random;

import game.sharedmemory.communication.*;
import game.sharedmemory.readerwriter.*;

public enum AtomicityMessagingService implements IReceiver{

	INSTANCE;

	/**
	 * dispatch messages of type {@link AtomicityMessage}
	 * to {@link AbstractAtomicityRegisterClient} or {@link AtomicityRegisterServer}.
	 */
	@Override
	public void onReceive(IPMessage msg)
	{
		// it only receives messages of type type {@link AtomicityMessage} from {@link MessagingService}
		assert (msg instanceof AtomicityMessage);

		/**
		 * Simulating the scenarios of "out of (receiving) order delivery" by introducing random latency
		 * to create more "old-new inversions".
		 *
		 * The average latency of read operations in 2-atomicity is about 80ms.
		 * Set the random delivery latency to 100ms.
		 *
		 * @author hengxin
		 * @date Aug 15, 2014
		 */

		/**
		 * Varying the random delay to simulate different degrees of asynchrony:
		 * 10ms, 20ms, 50ms, 100ms, 150ms, and 200ms
		 */
		try
		{
			Thread.sleep(new Random().nextInt(100));
		} catch (InterruptedException ie)
		{
			ie.printStackTrace();
		}

		/**
		 *  {@link AtomicityRegisterServer} is responsible for handling with messages
		 *  of types of {@link AtomicityReadPhaseMessage} and {@link AtomicityWritePhaseMessage}
		 *  while
		 *  {@link AtomicityRegisterClient} is responsible for handling with messages
		 *  of types of {@link AtomicityReadPhaseAckMessage} and {@link AtomicityWritePhaseAckMessage}
		 */
		if (msg instanceof AtomicityReadPhaseMessage || msg instanceof AtomicityWritePhaseMessage)
			AtomicityRegisterServer.INSTANCE.handleAtomicityMessage((AtomicityMessage) msg);
		else // (msg instanceof AtomicityReadAckPhaseMessage || msg instanceof AtomicityWriteAckPhaseMessage)
//			AtomicityRegisterClient.INSTANCE.handleAtomicityMessage((AtomicityMessage) msg);
			RegisterClientFactory.INSTANCE.getAtomicityRegisterClient().handleAtomicityMessage((AtomicityMessage) msg);
	}

}
