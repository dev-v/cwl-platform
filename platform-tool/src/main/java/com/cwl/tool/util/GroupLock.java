package com.cwl.tool.util;

import java.util.concurrent.locks.ReentrantLock;

public class GroupLock {

  private ReentrantLock[] locks;
  private int lockSize;

  public GroupLock(int lockSize) {
    this.lockSize = lockSize;
    locks = new ReentrantLock[lockSize];
    for (int i = 0; i < lockSize; i++) {
      locks[i] = new ReentrantLock();
    }
  }

  public GroupLock() {
    this(16);
  }


  public void lock(Object key) {
    locks[getIdx(key)].lock();
  }

  public void unlock(Object key) {
    locks[getIdx(key)].unlock();
  }

  private int getIdx(Object key) {
    int hash = key.hashCode();
    return (hash > 0 ? hash : -hash) % lockSize;
  }
}
