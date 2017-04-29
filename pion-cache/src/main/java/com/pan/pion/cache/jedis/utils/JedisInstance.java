package com.pan.pion.cache.jedis.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.MethodInvoker;

import com.pan.pion.cache.jedis.cluster.RedisCluster;
import com.pan.pion.cache.jedis.cluster.RedisClusterFactory;
import com.pan.pion.cache.jedis.standalone.StandaloneJedis;
import com.pan.pion.cache.redis.config.CacheConfig;
import com.pan.pion.common.exception.PionException;
import com.pan.pion.log.AppLogger;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;
import redis.clients.util.SafeEncoder;

/**
 * jedis实例
 * 
 * @author
 *
 */
public class JedisInstance {

	private static JedisInstance INSTANCE;

	private static final AppLogger LOG = AppLogger.getLogger(JedisInstance.class);

	private CacheConfig cacheConfig;

	private RedisCluster redisCluster;

	public Strings STRINGS = new Strings();

	public Lists LISTS = new Lists();

	public Sets SETS = new Sets();

	public Hash HASH = new Hash();

	public SortSet SORTSET = new SortSet();

	public Keys KEYS = new Keys();

	public static JedisInstance getInstance() {
		if (INSTANCE == null) {
			synchronized (JedisInstance.class) {
				if (INSTANCE == null) {
					INSTANCE = new JedisInstance();
				}
			}
		}
		return INSTANCE;
	}

	protected JedisInstance() {
		try {
			cacheConfig = CacheConfig.init();
		} catch (Exception e) {
			LOG.error("redis配置 初始化错误", e);
		}

		if (cacheConfig.isCluster()) {
			try {
				redisCluster = RedisClusterFactory.getCluster();
			} catch (Exception e) {
				LOG.error("redis 初始化错误", e);
			}
		}
	}

	/**
	 * 调用redis实际的操作
	 * 
	 * @param method
	 * @param args
	 * @return
	 */
	public Object invoke(String method, Object[] args) {
		Object targetObject = null;
		try {
			if (cacheConfig.isCluster()) {
				targetObject = redisCluster.getCluster();
			} else {
				targetObject = StandaloneJedis.getInstance();
			}
			MethodInvoker invoker = new MethodInvoker();
			invoker.setTargetObject(targetObject);
			invoker.setTargetMethod(method);
			invoker.setArguments(args);
			invoker.prepare();

			return invoker.invoke();
		} catch (Exception e) {
			LOG.error(String.format("invoke failed {method:%s},{args:%s}", method, args), e);
			throw new PionException(e);
		} finally {
			if (!cacheConfig.isCluster() && targetObject != null) {
				StandaloneJedis.returnResource((Jedis) targetObject);
			}
		}
	}

	public class Hash {
		public Hash() {
		}

		public long hdel(String key, String fieid) {
			return (long) invoke("hdel", new Object[] { key, new String[] { fieid } });
		}

		public boolean hexists(String key, String fieid) {
			return (boolean) invoke("hexists", new Object[] { key, fieid });
		}

		public String hget(String key, String fieid) {
			return (String) invoke("hget", new Object[] { key, fieid });
		}

		public byte[] hget(byte[] key, byte[] fieid) {
			return (byte[]) invoke("hget", new Object[] { key, fieid });
		}

		@SuppressWarnings("unchecked")
		public Map<String, String> hgetAll(String key) {
			return (Map<String, String>) invoke("hgetAll", new Object[] { key });
		}

		public long hset(String key, String fieid, String value) {

			return (long) invoke("hset", new Object[] { key, fieid, value });
		}

		public long hset(String key, String fieid, byte[] value) {
			return (long) invoke("hset", new Object[] { SafeEncoder.encode(key), SafeEncoder.encode(fieid), value });
		}

		public long hsetnx(String key, String fieid, String value) {
			return (long) invoke("hsetnx", new Object[] { key, fieid, value });
		}

		@SuppressWarnings("unchecked")
		public List<String> hvals(String key) {
			return (List<String>) invoke("hvals", new Object[] { key });
		}

		public long hincrby(String key, String fieid, long value) {
			return (long) invoke("hincrby", new Object[] { key, fieid, value });
		}

		@SuppressWarnings("unchecked")
		public Set<String> hkeys(String key) {
			return (Set<String>) invoke("hkeys", new Object[] { key });
		}

		public long hlen(String key) {
			return (long) invoke("hlen", new Object[] { key });
		}

		@SuppressWarnings("unchecked")
		public List<String> hmget(String key, String[] fieids) {
			return (List<String>) invoke("hmget", new Object[] { key, fieids });
		}

		@SuppressWarnings("unchecked")
		public List<byte[]> hmget(byte[] key, byte[][] fieids) {
			return (List<byte[]>) invoke("hmget", new Object[] { key, fieids });
		}

		public String hmset(String key, Map<String, String> map) {
			return (String) invoke("hmset", new Object[] { key, map });
		}

		public String hmset(byte[] key, Map<byte[], byte[]> map) {
			return (String) invoke("hmset", new Object[] { key, map });
		}
	}

	public class Keys {
		public Keys() {
		}

		public long expire(String key, int seconds) {
			return (long) invoke("expire", new Object[] { key, seconds });
		}

		public long expireAt(String key, long timestamp) {
			return (long) invoke("expireAt", new Object[] { key, timestamp });
		}

		public long ttl(String key) {
			return (long) invoke("ttl", new Object[] { key });
		}

		public long persist(String key) {
			return (long) invoke("persist", new Object[] { key });
		}

		public boolean exists(String key) {
			return (boolean) invoke("exists", new Object[] { key });
		}

		@SuppressWarnings("unchecked")
		public List<String> sort(String key) {
			return (List<String>) invoke("sort", new Object[] { key });
		}

		@SuppressWarnings("unchecked")
		public List<String> sort(String key, SortingParams parame) {
			return (List<String>) invoke("sort", new Object[] { key, parame });
		}

		public Long del(String key) {
			return (Long) invoke("del", new Object[] { key });
		}

		public Long del(byte[] key) {
			return (Long) invoke("del", new Object[] { key });
		}

		public String type(String key) {
			return (String) invoke("type", new Object[] { key });
		}
	}

	public class Lists {
		public Lists() {
		}

		public long llen(String key) {
			return llen(SafeEncoder.encode(key));
		}

		public long llen(byte[] key) {
			return (long) invoke("llen", new Object[] { key });
		}

		public String lset(byte[] key, long index, byte[] value) {
			return (String) invoke("lset", new Object[] { key, index, value });
		}

		public String lset(String key, long index, String value) {
			return lset(SafeEncoder.encode(key), index, SafeEncoder.encode(value));
		}

		public long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
			return linsert(SafeEncoder.encode(key), where, SafeEncoder.encode(pivot), SafeEncoder.encode(value));
		}

		public long linsert(byte[] key, BinaryClient.LIST_POSITION where, byte[] pivot, byte[] value) {
			return (long) invoke("linsert", new Object[] { key, where, pivot, value });
		}

		public String lindex(String key, long index) {
			return SafeEncoder.encode(lindex(SafeEncoder.encode(key), index));
		}

		public byte[] lindex(byte[] key, long index) {
			return (byte[]) invoke("lindex", new Object[] { key, index });
		}

		public String lpop(String key) {
			byte[] ret = lpop(SafeEncoder.encode(key));
			if ((ret == null) || (ret.length == 0)) {
				return null;
			}
			return SafeEncoder.encode(ret);
		}

		public byte[] lpop(byte[] key) {
			return (byte[]) invoke("lpop", new Object[] { key });
		}

		public String rpop(String key) {
			return (String) invoke("rpop", new Object[] { key });
		}

		public long lpush(String key, String value) {
			return lpush(SafeEncoder.encode(key), SafeEncoder.encode(value));
		}

		public long rpush(String key, String value) {
			return (long) invoke("rpush", new Object[] { key, new String[] { value } });
		}

		public long rpush(byte[] key, byte[] value) {
			return (long) invoke("rpush", new Object[] { key, new byte[][] { value } });
		}

		public long lpush(byte[] key, byte[] value) {
			return (long) invoke("lpush", new Object[] { key, new byte[][] { value } });
		}

		@SuppressWarnings("unchecked")
		public List<String> lrange(String key, long start, long end) {
			return (List<String>) invoke("lrange", new Object[] { key, start, end });
		}

		@SuppressWarnings("unchecked")
		public List<byte[]> lrange(byte[] key, long start, long end) {
			return (List<byte[]>) invoke("lrange", new Object[] { key, start, end });
		}

		public long lrem(byte[] key, long c, byte[] value) {
			return (long) invoke("lrem", new Object[] { key, c, value });
		}

		public long lrem(String key, int c, String value) {
			return lrem(SafeEncoder.encode(key), c, SafeEncoder.encode(value));
		}

		public String ltrim(byte[] key, long start, long end) {
			return (String) invoke("ltrim", new Object[] { key, start, end });
		}

		public String ltrim(String key, long start, long end) {
			return ltrim(SafeEncoder.encode(key), start, end);
		}

		public Long rpushx(final String key, final String value) {
			return (Long) invoke("rpushx", new Object[] { key, new String[] { value } });
		}

	}

	public class Sets {
		public Sets() {
		}

		public long sadd(String key, String member) {
			return (long) invoke("sadd", new Object[] { key, new String[] { member } });
		}

		public long sadd(byte[] key, byte[] member) {
			return (long) invoke("sadd", new Object[] { key, new byte[][] { member } });
		}

		public long scard(String key) {
			return (long) invoke("scard", new Object[] { key });
		}

		public boolean sismember(String key, String member) {
			return (boolean) invoke("sismember", new Object[] { key, member });
		}

		@SuppressWarnings("unchecked")
		public Set<String> smembers(String key) {
			return (Set<String>) invoke("smembers", new Object[] { key });
		}

		@SuppressWarnings("unchecked")
		public Set<byte[]> smembers(byte[] key) {
			return (Set<byte[]>) invoke("smembers", new Object[] { key });
		}

		public String spop(String key) {
			return (String) invoke("spop", new Object[] { key });
		}

		public long srem(String key, String member) {
			return (long) invoke("srem", new Object[] { key, member });
		}
	}

	public class SortSet {
		public SortSet() {
		}

		public long zadd(String key, double score, String member) {
			return (long) invoke("zadd", new Object[] { key, score, member });
		}

		public long zadd(String key, Map<String, Double> scoreMembers) {
			return (long) invoke("zadd", new Object[] { key, scoreMembers });
		}

		public long zcard(String key) {
			return (long) invoke("zcard", new Object[] { key });
		}

		public long zcount(String key, double min, double max) {
			return (long) invoke("zcount", new Object[] { key, min, max });
		}

		public long zlength(String key) {
			long len = 0L;
			Set<String> set = zrange(key, 0, -1);
			len = set.size();
			return len;
		}

		public double zincrby(String key, double score, String value) {
			return (long) invoke("zincrby", new Object[] { key, score, value });
		}

		@SuppressWarnings("unchecked")
		public Set<String> zrange(String key, long start, long end) {
			return (Set<String>) invoke("zrange", new Object[] { key, start, end });
		}

		@SuppressWarnings("unchecked")
		public Set<String> zrangeByScore(String key, double min, double max) {
			return (Set<String>) invoke("zrangeByScore", new Object[] { key, min, max });
		}

		public long zrank(String key, String member) {
			return (long) invoke("zrank", new Object[] { key, member });
		}

		public long zrevrank(String key, String member) {
			return (long) invoke("zrevrank", new Object[] { key, member });
		}

		public long zrem(String key, String member) {
			return (long) invoke("zrem", new Object[] { key, member });
		}

		public long zrem(String key) {
			return (long) invoke("zrem", new Object[] { key });
		}

		public long zremrangeByRank(String key, long start, long end) {
			return (long) invoke("zremrangeByRank", new Object[] { key, start, end });
		}

		public long zremrangeByScore(String key, double min, double max) {
			return (long) invoke("zremrangeByScore", new Object[] { key, min, max });
		}

		@SuppressWarnings("unchecked")
		public Set<String> zrevrange(String key, long start, long end) {
			return (Set<String>) invoke("zrevrange", new Object[] { key, start, end });
		}

		public double zscore(String key, String memebr) {
			return (double) invoke("zscore", new Object[] { key, memebr });
		}
	}

	public class Strings {
		public Strings() {
		}

		public String get(String key) {
			return (String) invoke("get", new Object[] { key });
		}

		public byte[] get(byte[] key) {
			return (byte[]) invoke("get", new Object[] { key });
		}

		public String setEx(String key, int seconds, String value) {
			return (String) invoke("setex", new Object[] { key, seconds, value });
		}

		public String setEx(byte[] key, int seconds, byte[] value) {
			return (String) invoke("setex", new Object[] { key, seconds, value });
		}

		public long setnx(String key, String value) {
			return (long) invoke("setnx", new Object[] { key, value });
		}

		public String set(String key, String value) {
			return set(SafeEncoder.encode(key), SafeEncoder.encode(value));
		}

		public String set(String key, byte[] value) {
			return set(SafeEncoder.encode(key), value);
		}

		public String set(byte[] key, byte[] value) {
			return (String) invoke("set", new Object[] { key, value });
		}

		public long setRange(String key, long offset, String value) {
			return (long) invoke("setRange", new Object[] { key, offset, value });
		}

		public long append(String key, String value) {
			return (long) invoke("append", new Object[] { key, value });
		}

		public long decrBy(String key, long number) {
			return (long) invoke("decrBy", new Object[] { key, number });
		}

		public long incrBy(String key, long number) {
			return (long) invoke("incrBy", new Object[] { key, number });
		}

		public String getrange(String key, long startOffset, long endOffset) {
			return (String) invoke("getrange", new Object[] { key, startOffset, endOffset });
		}

		public String getSet(String key, String value) {
			return (String) invoke("getSet", new Object[] { key, value });
		}

		public long strlen(String key) {
			return (long) invoke("getSet", new Object[] { key });
		}
	}
}
