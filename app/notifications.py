# Install the server library with:
#   pip install 'pusher>=1.4' --upgrade

from pusher import Pusher

pusher = Pusher(app_id=u'424832', key=u'575e480f6f4c0941387e', secret=u'd42c6b7d26dded3c235c', cluster=u'us2')

pusher.notify(['kittens'], {
  'gcm': {
    'notification': {
      'title': 'hello world',
      'icon': 'androidlogo'
    }
  }
})
