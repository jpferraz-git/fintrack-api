import type { Meta, StoryObj } from '@storybook/angular';
import { AlertsStatusbar } from './alerts-statusbar.component';

const meta: Meta<AlertsStatusbar> = {
  title: 'Components/AlertsStatusbar',
  component: AlertsStatusbar,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<AlertsStatusbar>;

export const Primary: Story = {
  args: {},
};
