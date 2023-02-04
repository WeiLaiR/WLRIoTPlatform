import Vue from 'vue'
import Main from './Message.vue'

let MessageConstructor = Vue.extend(Main)

let instance
let instances = []
let seed = 1
const Message = function (options,color,icon,typeClass) {
    options = options || {}
    if (typeof options === 'string') {
        options = {
            message: options,
            color:color,
            icon:icon,
            typeClass:typeClass
        }
    }
    let id = 'message_' + seed++
    instance = new MessageConstructor({
        data: options
    })
    instance.id = id
    instance.vm = instance.$mount()
    document.body.appendChild(instance.vm.$el)
    instance.vm.visible = true
    instance.dom = instance.vm.$el
    instance.dom.style.zIndex = 10000
    instances.push(instance)
    return instance.vm
}

Message.success = function(option){
    Message(option,"#67c23a","circle-check","message--success")
}
Message.error = function(option){
    Message(option,"#CC0033","triangle-exclamation","message--error")
}

Message.close = function (id) {
    for (let i = 0, len = instances.length; i < len; i++) {
        if (id === instances[i].id) {
            instances.splice(i, 1)
            break
        }
    }
}

Message.closeAll = function () {
    for (let i = instances.length - 1; i >= 0; i--) {
        instances[i].close()
    }
}

export default Message
