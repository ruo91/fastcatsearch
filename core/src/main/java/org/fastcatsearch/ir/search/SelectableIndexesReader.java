package org.fastcatsearch.ir.search;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.fastcatsearch.ir.settings.ReferencableFieldSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SelectableIndexesReader<T extends ReferencableIndexReader, S extends ReferencableFieldSetting> implements Cloneable {
	protected static Logger logger = LoggerFactory.getLogger(SelectableIndexesReader.class);

	protected List<T> readerList;
	protected List<S> indexSettingList;

	protected Map<String, IndexRef<T>> selectIndexCache;

	public SelectableIndexesReader() {
	}

	public IndexRef<T> selectIndexRef(String[] fieldList) throws IOException {
		IndexRef<T> indexRef = new IndexRef<T>();

		// 단일인덱스를 조합한다.

		for (int i = 0; i < fieldList.length; i++) {
			String fieldId = fieldList[i];
			T reader = null;
			if (fieldId != null) {
			
				for (int j = 0; j < indexSettingList.size(); j++) {
					S setting = indexSettingList.get(j);
					String indexFieldId = setting.getId();
					// 동일필드명을 찾는다.
					if (indexFieldId.equalsIgnoreCase(fieldId)) {
						reader = cloneReader(j);
						break;
					}
	
				}
			}
			indexRef.add(fieldId, reader);

			if (reader == null && fieldId !=null) {
//				logger.error("색인된 필드를 찾지못함.>>{}", fieldId);
				throw new IOException("Field is not exist \""+fieldId+"\"");
			}
		}

		return indexRef;
	}

	protected abstract T cloneReader(int indexSequence);

	public T getIndexReader(int indexSequence) {
		return readerList.get(indexSequence);
	}

	public void close() throws IOException {
		if (readerList != null) {
			for (T reader : readerList) {
				if (reader != null) {
					reader.close();
				}
			}
		}
	}

}
