/*
* AllBinary Open License Version 1
* Copyright (c) 2011 AllBinary
* 
* By agreeing to this license you and any business entity you represent are
* legally bound to the AllBinary Open License Version 1 legal agreement.
* 
* You may obtain the AllBinary Open License Version 1 legal agreement from
* AllBinary or the root directory of AllBinary's AllBinary Platform repository.
* 
* Created By: Travis Berthelot
* 
*/
package allbinary.graphics.form.item.validation;

import org.allbinary.graphics.form.item.TextFieldItem;
import org.allbinary.util.BasicArrayList;

import abcs.logic.java.bool.BooleanFactory;
import allbinary.logic.control.validate.ValidatorBase;

public class TextFieldItemValidator extends ValidatorBase
{
    private final TextFieldItem textFieldItem;

    private int min;
    private int max;
    private boolean allowOnEmpty;

    public TextFieldItemValidator(TextFieldItem textFieldItem, int min, int max, boolean allowOnEmpty)
    {
        this.textFieldItem = textFieldItem;
        
        this.min = min;
        this.max = max;
        this.allowOnEmpty = allowOnEmpty;
    }
    
    //@Override
    public Boolean isValid()
    {
        BooleanFactory booleanFactory = BooleanFactory.getInstance();
        
        Boolean result = booleanFactory.TRUE;

        final String string = this.textFieldItem.getString();

        int textLength = string.length();

        if ((textLength == 0 && this.allowOnEmpty) || textLength > this.min && textLength < this.max)
        {
            // PreLogUtil.put(, this, "toVector");
        } else
        {
            if(textLength < this.min)
            {
                result = booleanFactory.FALSE;
            }
            else
                if(textLength > this.max)
                {
                    result = booleanFactory.FALSE;
                }
        }
        return result;
    }
    
    //@Override
    public BasicArrayList toList()
    {
        final BasicArrayList list = new BasicArrayList();        
        final String string = this.textFieldItem.getString();

        int textLength = string.length();
        
        if (textLength > this.min && textLength < this.max)
        {
            // PreLogUtil.put(, this, "toVector");
        } 
        else
        {
            final String name = this.textFieldItem.getLabel().substring(0, this.textFieldItem.getLabel().length() - 2);
            
            if(textLength < this.min)
            {
                list.add(name + " is to short");
            }
            else
                if(textLength > this.max)
                {
                    list.add(name + " is to long");
                }
        }
        return list;
    }
}
