package org.andengine.util.algorithm.path;

import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.algorithm.path.astar.tile.pool.IPool;

/**
 * (c) 2010 Nicolas Gramlich (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 23:00:24 - 16.08.2010
 */
public class Path implements IPool {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int[] mXs;
	private int[] mYs;
	private GenericPool<Path> mPool;
	private boolean mSwitchXY = false;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Path(GenericPool<Path> pPool) {
		this.mPool = pPool;
	}

	public Path(final int pLength) {
		this.setup(pLength);
	}

	public void setup(final int pLength) {
		this.mXs = new int[pLength];
		this.mYs = new int[pLength];
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public int getLength() {
		return this.mXs.length;
	}

	public int getFromX() {
		return this.getX(0);
	}

	public int getFromY() {
		return this.getY(0);
	}

	public int getToX() {
		return this.getX(this.getLength() - 1);
	}

	public int getToY() {
		return this.getY(this.getLength() - 1);
	}

	public int getX(final int pIndex) {
		return this.mXs[pIndex];
	}

	public int getY(final int pIndex) {
		return this.mYs[pIndex];
	}

	/**
	 * Should we switch the node X and Y round? <br>
	 * useful if we're doing an isometric and need to calculate the correct
	 * direction
	 * 
	 * @param pSwitch
	 *            <code>true</code> to switch <code>false</code> to not switch
	 */
	public void switchXY(final boolean pSwitch) {
		this.mSwitchXY = pSwitch;
	}

	/**
	 * Are we switching X and Y around?
	 * useful if we're doing an isometric and need to calculate the correct
	 * direction
	 * 
	 * @return<code>true</code> to switch <code>false</code> to not switch
	 */
	public boolean isSwitchingXY() {
		return this.mSwitchXY;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// IPool
	// ===========================================================
	@Override
	public void reset() {
		this.mXs = null;
		this.mYs = null;
	}

	@Override
	public void destroy() {
		if (this.mPool != null) {
			this.mPool.recyclePoolItem(this);
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void set(final int pIndex, final int pX, final int pY) {
		this.mXs[pIndex] = pX;
		this.mYs[pIndex] = pY;
	}

	public boolean contains(final int pX, final int pY) {
		final int[] xs = this.mXs;
		final int[] ys = this.mYs;
		for (int i = this.getLength() - 1; i >= 0; i--) {
			if (xs[i] == pX && ys[i] == pY) {
				return true;
			}
		}
		return false;
	}

	public Direction getDirectionToPreviousStep(final int pIndex) {
		if (pIndex == 0) {
			return null;
		} else {
			final int dX = this.getX(pIndex - 1) - this.getX(pIndex);
			final int dY = this.getY(pIndex - 1) - this.getY(pIndex);
			if (this.mSwitchXY) {
				return Direction.fromDelta(dY, dX);
			}
			return Direction.fromDelta(dX, dY);
		}
	}

	public Direction getDirectionToNextStep(final int pIndex) {
		if (pIndex == this.getLength() - 1) {
			return null;
		} else {
			final int dX = this.getX(pIndex + 1) - this.getX(pIndex);
			final int dY = this.getY(pIndex + 1) - this.getY(pIndex);
			if (this.mSwitchXY) {
				return Direction.fromDelta(dY, dX);
			}
			return Direction.fromDelta(dX, dY);
		}
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
