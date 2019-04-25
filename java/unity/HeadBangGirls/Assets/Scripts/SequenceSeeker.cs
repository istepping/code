using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using System.Xml;
using System.Xml.Serialization;

//扫描序列，获得近一个元素的索引。
public class SequenceSeeker<ElementType>
	where ElementType: MusicalElement
{	//设置扫描的序列数据
	public void SetSequence( List<ElementType> sequence ){
		m_sequence = sequence;
		m_nextIndex=0;
		m_currentBeatCount=0;
		m_isJustPassElement=false;
	}
	//显示最接近的下一个元素的索引号码
	public int nextIndex{
			get{return m_nextIndex;}
	}
	//通过元素的触发位置的时候
	public bool isJustPassElement{
			get{return m_isJustPassElement;}
	}

	//每帧处理
	public void ProceedTime(float deltaBeatCount){

		// 现在进行时刻
		m_currentBeatCount += deltaBeatCount;
		// 把表示“皱纹位置前进了”的瞬间的标志丢失。
		m_isJustPassElement = false;

		int		index = find_next_element(m_nextIndex);

		// 找到了下一个元素
		if(index!=m_nextIndex){

			// 跟新移动位置
			m_nextIndex = index;

			// 表示“移动位置前进”的瞬间的标志。
			m_isJustPassElement=true;
		}
	}
	//扫描
	public void Seek(float beatCount){

		m_currentBeatCount = beatCount;

		int		index = find_next_element(0);

		m_nextIndex = index;
	}

	// m_currentBeatCount 寻找某个元素。
	//
	private int	find_next_element(int start_index)
	{
		// 以表示“过了最后的标记时刻”的值进行初始化。
		int ret = m_sequence.Count;

		for (int i = start_index;i < m_sequence.Count; i++)
		{
			// m_currentBeatCount 在后面的马车=找到了。
			if(m_sequence[i].triggerBeatTiming > m_currentBeatCount)
			{
				ret = i;
				break;
			}
		}

		return(ret);
	}

//private variables
	int		m_nextIndex = 0;				//移动位置（=从当前时刻来看，下一个元件的索引）。
	float	m_currentBeatCount = 0;			//現在時刻.
	bool	m_isJustPassElement = false;	//只有移动位置在前进的帧中变成true。

	List<ElementType> m_sequence;			//序列数据
}

